package com.fgnb.service;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.fgnb.agent.AgentApi;
import com.fgnb.domain.*;
import com.fgnb.dto.ActionDTO;
import com.fgnb.enums.TestPlanBeforeType;
import com.fgnb.enums.TestTaskRunMode;
import com.fgnb.enums.TaskStatus;
import com.fgnb.exception.BusinessException;
import com.fgnb.mapper.TestPlanBeforeMapper;
import com.fgnb.mapper.TestTaskMapper;
import com.fgnb.testngcode.TestNGCodeConverter;
import com.fgnb.utils.UUIDUtil;
import com.fgnb.vo.PageVo;
import io.restassured.response.Response;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.validation.Valid;
import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by jiangyitao.
 */
@Service
@Slf4j
public class TestTaskService extends BaseService{
    @Autowired
    private TestTaskMapper testTaskMapper;
    @Autowired
    private DeviceService deviceService;
    @Autowired
    private TestPlanService testPlanService;
    @Autowired
    private TestPlanBeforeMapper testPlanBeforeMapper;

    @Autowired
    private TestTaskReportService testTaskReportService;

    @Autowired
    private TestTaskDeviceService testTaskDeviceService;

    @Autowired
    private ActionService actionService;

    @Autowired
    private GlobalVarService globalVarService;

    @Autowired
    private AgentApi agentApi;

    public void updateTestTask(TestTask testTask){
        if(testTask.getTestTaskId() == null){
            throw new BusinessException("testTaskId不能为空");
        }
        int row = testTaskMapper.updateTestTask(testTask);
        if(row != 1){
            throw new BusinessException("更新testTask失败");
        }
    }

    @Transactional
    public void commitTestTask(@Valid TestTask testTask) {
        //1.校验命名
        TestTask dbTestTask = testTaskMapper.findTestTaskByProjectIdAndTestTaskName(testTask);
        if(dbTestTask != null){
            throw new BusinessException("测试任务命名冲突");
        }

        //2.获取能够开启UiAutomatorServer的设备 这个时候agent已经把设备改为使用中了
        List<DeviceInfo> deviceUiAutomaorInfos = getDeviceUiAutomatorInfo(testTask);
        log.info("设备信息 -> {}",JSON.toJSONString(deviceUiAutomaorInfos));
        if(CollectionUtils.isEmpty(deviceUiAutomaorInfos)){
            throw new BusinessException("无法执行任务，找不到可执行任务的设备");
        }

        //3.修改数据库设备的信息 (使用人：testTaskName 使用中)
        for(DeviceInfo deviceInfo: deviceUiAutomaorInfos) {
            deviceService.setDeviceUserName(deviceInfo.getDeviceId(),"自动化测试任务:"+testTask.getTestTaskName());
        }

        //4.查出所有测试用例
        List<Integer> testCaseIds = testPlanService.findTestCaseIdsByTestPlanId(testTask.getTestPlanId());
        log.info("用例Id -> {}",testCaseIds);
        if(CollectionUtils.isEmpty(testCaseIds)){
            throw new BusinessException("测试用例不能为空");
        }

        //5.根据不同用例分发策略 给设备分配用例
        Map<DeviceInfo, List<Integer>> deviceTestCasesMap = allocationTestCasesForDevice(testTask, deviceUiAutomaorInfos, testCaseIds);
        log.info("用例分配结果 -> {}",deviceTestCasesMap);


        //6.保存测试任务，获取测试任务id
        testTask.setCreatorUid(getUid());
        testTask.setStatus(TaskStatus.RUNNING.getStatus());
        testTask.setStartTime(new Date());
        int testTaskRow = testTaskMapper.addTestTask(testTask);
        if(testTaskRow != 1){
            throw new BusinessException("添加任务失败，请稍后重试");
        }
        //7.保存TestTaskDevice
        for(DeviceInfo deviceInfo: deviceUiAutomaorInfos) {
            //这里也可以改成 批量插入
            testTaskDeviceService.saveTestTaskDevice(deviceInfo.getDeviceId(),testTask.getTestTaskId());
        }

        //8.根据testPlanId获取beforeSuite beforeMethod
        Integer beforeSuiteActionId = null;
        Integer beforeMethodAcitonId = null;

        List<TestPlanBefore> testPlanBefores = testPlanBeforeMapper.findByTestPlanId(testTask.getTestPlanId());
        for(TestPlanBefore testPlanBefore:testPlanBefores){
            if(testPlanBefore.getType() == TestPlanBeforeType.BEFORE_SUITE.getType()){
                beforeSuiteActionId = testPlanBefore.getActionId();
            }else if(testPlanBefore.getType() == TestPlanBeforeType.BEFORE_METHOD.getType()){
                beforeMethodAcitonId = testPlanBefore.getActionId();
            }
        }

        //BeforeSuiteAction
        Action beforeSuiteAction = null;
        if(beforeSuiteActionId!=null){
            beforeSuiteAction = actionService.buildActionTree(beforeSuiteActionId);
        }
        //BeforeMethodAction
        Action beforeMethodAction = null;
        if(beforeMethodAcitonId != null){
            beforeMethodAction = actionService.buildActionTree(beforeMethodAcitonId);
        }

        //9.保存测试任务报告，获取测试任务报告id
        TestTaskReport testTaskReport = testTaskReportService.addTestTaskReport(testTask.getTestTaskId());


        //10.每个设备生成相应的测试代码 Map -> 类全名 + 代码内容
        Map<DeviceInfo,Map<String,String>> deviceCodes = new HashMap<>();
        for(DeviceInfo deviceInfo : deviceTestCasesMap.keySet()){
            Map<String,String> codes = new HashMap<>();
            deviceCodes.put(deviceInfo,codes);
            //beforeSuite
            if(beforeSuiteAction!=null){
                ActionDTO actionDTO = convertActionToActionDto(deviceInfo, beforeSuiteAction);
                String beforeSuiteCode;
                String beforeSuiteClassName = "BeforeSuite_"+ UUIDUtil.getUUID();
                try {
                    beforeSuiteCode = new TestNGCodeConverter().convertBeforeSuite(beforeSuiteClassName,actionDTO,"beforeSuite.ftl");
                    log.info("uid:{},转换BeforeSuite代码:{}",getUid(),beforeSuiteCode);
                } catch (Exception e) {
                    throw new BusinessException("beforeSuite代码转换出错",e);
                }
                codes.put(beforeSuiteClassName,beforeSuiteCode);
            }
            //测试用例
            List<Integer> actionIds = deviceTestCasesMap.get(deviceInfo);
            for(Integer actionId : actionIds){
                Action action = actionService.buildActionTree(actionId);
                //如果有beforeMethod则把beforeMethod的action插入到第一步   (因为使用beforeMethod会有问题,这里改用这种方法比较方便)
                if(beforeMethodAcitonId != null){
                    ActionStep beforeMethodActionStep = new ActionStep();
                    beforeMethodActionStep.setStepActionId(beforeMethodAction.getActionId());
                    beforeMethodActionStep.setStepActionName(beforeMethodAction.getActionName());
                    beforeMethodActionStep.setActionStepId(beforeMethodAction.getActionId());
                    beforeMethodActionStep.setActionStepParamValues(new ArrayList<>());
                    beforeMethodActionStep.setAction(beforeMethodAction);
                    action.getActionSteps().add(0,beforeMethodActionStep);
                }
                ActionDTO actionDTO = convertActionToActionDto(deviceInfo, action);
                //测试用例 提供以下信息 给agent 的com.fgnb.excutor.TestListenerForTestCase
                actionDTO.setTestTaskId(testTask.getTestTaskId());
                actionDTO.setTestTaskReportId(testTaskReport.getTestTaskReportId());

                String testCaseCode;
                String testCaseClassName = "TestCase_"+UUIDUtil.getUUID();
                try {
                    testCaseCode = new TestNGCodeConverter().convertTestCase(testCaseClassName,actionDTO,"testCase.ftl");
                    log.info("uid:{},转换TestCase代码:{}",getUid(),testCaseCode);
                } catch (Exception e) {
                    throw new BusinessException("testCase代码转换出错",e);
                }
                codes.put(testCaseClassName,testCaseCode);
            }
        }
        //11.多线程发送代码到agent执行
        ExecutorService threadPool = Executors.newCachedThreadPool();
        CountDownLatch countDownLatch = new CountDownLatch(deviceUiAutomaorInfos.size());
        for(DeviceInfo deviceInfo:deviceCodes.keySet()){
            threadPool.execute(()->{
                try{
                    Response response = agentApi.commitTestTask(deviceInfo.getAgentIp(),deviceInfo.getDeviceId(),deviceCodes.get(deviceInfo));
                    log.info("[{}]提交任务，agent返回 -> {}",deviceInfo.getDeviceId(),response.asString());
                    if(!"1".equals(response.path("status"))){
                        //提交任务失败 todo 把设备还原回闲置
                    }
                }catch (Exception e){
                    log.error("[{}]提交任务失败",deviceInfo.getDeviceId(),e);
                    // todo 把设备还原回闲置
                }
                countDownLatch.countDown();
            });
        }
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            log.error(e.getMessage(),e);
        }
        threadPool.shutdown();
    }

    private ActionDTO convertActionToActionDto(DeviceInfo deviceInfo, Action action) {
        ActionDTO actionDTO = new ActionDTO();
        BeanUtils.copyProperties(action,actionDTO);
        actionDTO.setAgentIp(deviceInfo.getAgentIp());
        actionDTO.setDeviceId(deviceInfo.getDeviceId());
        actionDTO.setUiautomatorServerPort(deviceInfo.getPort());
        //添加全局变量
        List<GlobalVar> globalVars = globalVarService.findByProjectId(action.getProjectId());
        actionDTO.setGlobalVars(globalVars);
        return actionDTO;
    }

    /**
     * 根据不同用例分发模式 分发用例
     * @param testTask
     * @param deviceUiAutomaorInfos
     * @param testCaseIds
     * @return deviceId:[actionId(testcase).....]
     */
    private Map<DeviceInfo,List<Integer>> allocationTestCasesForDevice(TestTask testTask, List<DeviceInfo> deviceUiAutomaorInfos, List<Integer> testCaseIds) {
        Map<DeviceInfo,List<Integer>> deviceActionMap = new HashMap<>();
        if(testTask.getRunMode() == TestTaskRunMode.COMPATIBLE.getType()){
            //兼容模式
            for(DeviceInfo deviceUiautomatorInfo : deviceUiAutomaorInfos){
                deviceActionMap.put(deviceUiautomatorInfo,testCaseIds);
            }
        }else if(testTask.getRunMode() == TestTaskRunMode.EFFICIENCY.getType()){
            //当前分配到第几个设备
            int deviceIndex = 0;
            for(int i=0;i<testCaseIds.size();i++){
                DeviceInfo deviceInfo = deviceUiAutomaorInfos.get(deviceIndex);
                if(deviceActionMap.get(deviceInfo) == null){
                    List<Integer> deviceIds = new ArrayList<>();
                    deviceIds.add(testCaseIds.get(i));
                    deviceActionMap.put(deviceInfo,deviceIds);
                }else{
                    deviceActionMap.get(deviceInfo).add(testCaseIds.get(i));
                }
                deviceIndex++;
                //最后一个设备 重返第一个设备
                if(deviceIndex == deviceUiAutomaorInfos.size()){
                    deviceIndex = 0;
                }
            }
        }
        return deviceActionMap;
    }

    /**
     * 将能够开启UiAutomatorServer的设备获取到
     * @param testTask
     * @return  [{"msg":"启动UiAutomatorServer成功","port":8500,"deviceId":"bebe49a2","agentIp":"192.168.1.101","canUse":true},{xxx},{xxx}]
     */
    private List<DeviceInfo> getDeviceUiAutomatorInfo(TestTask testTask) {
        //多线程添加 这里用Vector
        List<DeviceInfo> deviceUiAutomaorInfos = new Vector<>();
        //待执行测试任务的设备列表
        List<Device> devices = deviceService.findByDeviceIds(testTask.getTestTaskDeviceIds());
        if(!CollectionUtils.isEmpty(devices)){
            ExecutorService threadPool = Executors.newCachedThreadPool();
            CountDownLatch countDownLatch = new CountDownLatch(devices.size());
            for(int i = 0;i<devices.size();i++){
                Device device = devices.get(i);
                threadPool.execute(()->{
                    try {
                        //请求打开UiAutomatorServer
                        Response response = agentApi.openUiAutomatorServer(device.getAgentIp(), device.getDeviceId());
                        log.info("[{}]打开UiautomatorServer -> {}",device.getDeviceId(),response.asString());
                        if("1".equals(response.path("status"))){
                            //设备UiAutomatorServer可用 并已开启
                            boolean canUse = response.path("data.canUse");
                            if(canUse){
                                DeviceInfo deviceInfo = new DeviceInfo();
                                deviceInfo.setAgentIp(device.getAgentIp());
                                deviceInfo.setDeviceId(device.getDeviceId());
                                deviceInfo.setCanUse(response.path("data.canUse"));
                                deviceInfo.setPort(response.path("data.port"));
                                deviceInfo.setMsg(response.path("data.msg"));
                                deviceUiAutomaorInfos.add(deviceInfo);
                            }
                        }
                    }catch (Exception e){
                        log.error("处理设备打开UiautomatorServer过程出错",e);
                    }
                    countDownLatch.countDown();
                });
            }
            //等待所有设备openUiAutomatorServer执行完成
            try {
                countDownLatch.await();
            } catch (InterruptedException e) {
               log.error("等待所有任务执行完成出现异常",e);
            }
            threadPool.shutdown();
        }
        return deviceUiAutomaorInfos;
    }

    @Data
    public static class DeviceInfo {
        private Boolean canUse;
        private String deviceId;
        private String agentIp;
        private Integer port;
        private String msg;
    }

    /**
     * 动态查询测试任务列表
     * @param testTask
     * @return
     */
    public PageVo dynamicQuery(TestTask testTask) {
        PageHelper.startPage(testTask.getPageIndex(),testTask.getCountPerPage());
        List<TestTask> testTasks = testTaskMapper.dynamicQuery(testTask);
        return PageVo.convert(new PageInfo(testTasks));
    }
}
