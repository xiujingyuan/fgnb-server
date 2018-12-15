package com.fgnb.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.fgnb.agent.AgentApi;
import com.fgnb.domain.*;
import com.fgnb.dto.ActionDTO;
import com.fgnb.exception.BusinessException;
import com.fgnb.mapper.*;
import com.fgnb.testngcode.TestNGCodeConverter;
import com.fgnb.utils.UUIDUtil;
import com.fgnb.vo.PageVo;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;

/**
 * Created by jiangyitao.
 */
@Service
@Slf4j
public class ActionService extends BaseService{

    @Autowired
    private ActionMapper actionMapper;
    @Autowired
    private ActionParamMapper actionParamMapper;
    @Autowired
    private ActionVarMapper actionVarMapper;
    @Autowired
    private ActionStepMapper actionStepMapper;
    @Autowired
    private ActionStepParamValueMapper actionStepParamValueMapper;

    @Autowired
    private AgentApi agentApi;

    @Transactional
    public void addAction(Action action) {

        action.setCreatorUid(getUid());
        action.setCreateTime(new Date());

        try{
            int actionRow = actionMapper.addAction(action);
            if(actionRow != 1){
                throw new BusinessException("添加action失败");
            }
        }catch (DuplicateKeyException e){
            throw new BusinessException("命名冲突");
        }

        //插入actionParam、ActionVar，actionStep，actionStepParamValue数据
        handleActionRelatedData(action);
    }

    /**
     * 插入ActionVar，actionStep，actionStepParamValue数据
     */
    private void handleActionRelateDataWithOutActionParams(Action action){
        //3.ActionVar表
        List<ActionVar> actionVars = action.getActionVars();
        if(actionVars!=null && actionVars.size()>0){
            //添加上actionId
            for(ActionVar actionVar:actionVars){
                actionVar.setActionId(action.getActionId());
            }
            int actionVarRow = actionVarMapper.addActionVars(actionVars);
            if(actionVarRow!= actionVars.size()){
                throw new BusinessException("添加actionVar失败");
            }
        }
        //4.actionStep表
        List<ActionStep> actionSteps = action.getActionSteps();
        if(actionSteps == null || actionSteps.size() == 0){
            throw new BusinessException("action步骤不能为空");
        }
        //给每个步骤加上actionId
        for(ActionStep actionStep:actionSteps){
            actionStep.setActionId(action.getActionId());
        }
        int actionStepsRow = actionStepMapper.addActionSteps(actionSteps);
        if(actionStepsRow!= actionSteps.size()){
            throw new BusinessException("添加actionStep失败");
        }
        //5.actionStepParamValue表
        List<ActionStepParamValue> actionStepParamValues = new ArrayList<>();
        for(ActionStep actionStep:actionSteps){
            List<ActionStepParamValue> values = actionStep.getActionStepParamValues();
            //加上actionStepId
            values.forEach(value->{
                value.setActionStepId(actionStep.getActionStepId());
            });
            //把所有action步骤的参数值都放入actionStepParamValues
            actionStepParamValues.addAll(values);
        }
        if(!CollectionUtils.isEmpty(actionStepParamValues)){
            int rows = actionStepParamValueMapper.addActionStepParamValues(actionStepParamValues);
            if(rows!=actionStepParamValues.size()){
                throw new BusinessException("添加actionStepParamValues失败");
            }
        }
    }
    /**
     * 插入actionParam，ActionVar，actionStep，actionStepParamValue数据
     * @param action
     */
    private void handleActionRelatedData(Action action) {
        //2.actionParam表
        List<ActionParam> actionParams = action.getActionParams();
        if(actionParams!=null && actionParams.size()>0){
            //添加上actionId
            for(ActionParam actionParam:actionParams){
                actionParam.setActionId(action.getActionId());
            }
            int actionParamRow = actionParamMapper.addActionParams(actionParams);
            if(actionParamRow!= actionParams.size()){
                throw new BusinessException("添加actionParam失败");
            }
        }
        handleActionRelateDataWithOutActionParams(action);
    }

    public List<Action> findSelectableActions(Integer projectId) {
        return actionMapper.findSelectableActions(projectId);
    }

    public PageVo findActionsByPageId(Integer pageId, Integer pageIndex, Integer countPerPage) {
        PageHelper.startPage(pageIndex,countPerPage);
        List<Action> actions = actionMapper.findActionsByPageId(pageId);
        return PageVo.convert(new PageInfo(actions));
    }

    public void deleteAction(Integer actionId) {
        int row = actionMapper.deleteAction(actionId);
        if(row!=1){
            throw new BusinessException("删除失败，请稍后重试");
        }
        //todo 删除其他表相关内容 如：ActionParam ActionVar 等等
    }

    @Transactional
    public void updateAction(Action action) {
        if(action.getActionId()==null){
            throw new BusinessException("actionId不能为空");
        }

        action.setUpdatorUid(getUid());
        action.setUpdateTime(new Date());

        try{
            //1.修改action表
            int row = actionMapper.updateAction(action);
            if(row!=1){
                throw new BusinessException("更新action失败");
            }
        }catch (DuplicateKeyException e){
            throw new BusinessException("命名冲突");
        }

        //2.删除ActionParam
        //fixbug:当删除action参数时，会导致引用该action的步骤，无法获取到Action参数值那一列相关的参数名和参数描述
        //actionParamMapper.deleteActionParamsByActionId(action.getActionId());
        //3.删除ActionVar
        actionVarMapper.deleteActionVarsByActionId(action.getActionId());
        //4.删除ActonStep
        actionStepMapper.deleteActionStepsByActionId(action.getActionId());
        //5.插入ActionVar，actionStep，actionStepParamValue数据
        handleActionRelateDataWithOutActionParams(action);
    }

    public Action getActionDetailInfoByActionId(Integer actionId) {
        return actionMapper.getActionDetailInfoByActionId(actionId);
    }

    public void debug(ActionDTO actionDTO) {
        if(StringUtils.isEmpty(actionDTO.getAgentIp()) ||
                StringUtils.isEmpty(actionDTO.getDeviceId()) ||
                StringUtils.isEmpty(actionDTO.getUiautomatorServerPort())){
            throw new BusinessException("请选择一台手机远程使用");
        }
        //检查设备是否可调试action
        try{
            Response response = agentApi.checkDeviceCanDebugAction(actionDTO.getAgentIp(), actionDTO.getUiautomatorServerPort());
            if(!"1".equals(response.path("status"))){
                throw new BusinessException(response.path("msg"));
            }
            log.info("检查设备是否可调试action -> {}",response.asString());
        }catch (Exception e){
            throw new BusinessException(e.getMessage(),e);
        }

        //这个地方因为可能修改过最上层action的相关信息 所以不直接通过actionId buildActionTree
        //处理actionStep
        List<ActionStep> actionSteps = actionDTO.getActionSteps();
        for(ActionStep actionStep: actionSteps){
            Action action = buildActionTree(actionStep.getStepActionId());
            actionStep.setAction(action);
        }
        //给未保存过的action赋予一个临时的id
        if(actionDTO.getActionId() == null){
            //默认设置为0
            actionDTO.setActionId(0);
        }

        String testNGCode;
        String testClassName = "DebugClass_"+ UUIDUtil.getUUID();
        try{
            //将调试的action转换为testng代码
            testNGCode = new TestNGCodeConverter().convertDebugAction(testClassName,actionDTO,"debugAction.ftl");
        }catch (Exception e){
            throw new BusinessException("代码转换出错，格式错误",e);
        }

        log.info("uid:{},转换代码:{}",getUid(),testNGCode);

        //将代码发送到agent执行
        try{
            Response response = agentApi.debugAction(actionDTO.getAgentIp(), testClassName,testNGCode);
            if(!"1".equals(response.path("status"))){
                throw new BusinessException(response.path("msg"));
            }
        }catch (Exception e){
            throw new BusinessException(e.getMessage(),e);
        }
        //执行成功

    }

    /**
     * 构建ActionTree
     * @return
     */
    public Action buildActionTree(Integer actionId){
       return actionMapper.buildActionTree(actionId);
    }

    /**
     * 动态查询action列表
     * @param action
     * @return
     */
    public PageVo dynamicQuery(Action action) {
        PageHelper.startPage(action.getPageIndex(),action.getCountPerPage());
        return PageVo.convert(new PageInfo(actionMapper.dynamicQuery(action)));
    }

    public List<Action> findActionByProjectIdAndActionType(Integer projectId, Integer actionType) {
        return actionMapper.findActionByProjectIdAndActionType(projectId,actionType);
    }

    public List<Map> findCreatorByProjectIdAndActionType(Integer projectId, Integer actionType) {
        return actionMapper.findCreatorByProjectIdAndActionType(projectId, actionType);
    }

    public List<Map> findUpdatorByProjectIdAndActionType(Integer projectId, Integer actionType){
        return actionMapper.findUpdatorByProjectIdAndActionType(projectId, actionType);
    }
}
