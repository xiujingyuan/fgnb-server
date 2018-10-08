package com.fgnb.service;

import com.fgnb.domain.TestTask;
import com.fgnb.domain.TestTaskDevice;
import com.fgnb.enums.TaskStatus;
import com.fgnb.exception.BusinessException;
import com.fgnb.mapper.TestTaskDeviceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Created by jiangyitao.
 */
@Service
public class TestTaskDeviceService {

    @Autowired
    private TestTaskDeviceMapper testTaskDeviceMapper;
    @Autowired
    private TestTaskReportService testTaskReportService;

    @Autowired
    private TestTaskService testTaskService;

    public void deviceTaskStart(Integer testTaskId, String deviceId) {
        TestTaskDevice testTaskDevice = testTaskDeviceMapper.findByTestTaskIdAndDeviceId(testTaskId, deviceId);
        if(testTaskDevice == null){
            throw new RuntimeException("设备测试任务不存在");
        }
        testTaskDevice.setStatus(TaskStatus.RUNNING.getStatus());
        testTaskDevice.setStartTime(new Date());
        int row = testTaskDeviceMapper.update(testTaskDevice);
        if(row != 1){
            throw new BusinessException("更新testTaskDevice失败");
        }
    }

    @Transactional
    public void deviceTaskFinish(Integer testTaskId, String deviceId) {
        TestTaskDevice testTaskDevice = testTaskDeviceMapper.findByTestTaskIdAndDeviceId(testTaskId, deviceId);
        if(testTaskDevice == null){
            throw new RuntimeException("设备测试任务不存在");
        }
        testTaskDevice.setStatus(TaskStatus.FINISHED.getStatus());
        testTaskDevice.setEndTime(new Date());
        int row = testTaskDeviceMapper.update(testTaskDevice);
        if(row != 1){
            throw new BusinessException("更新testTaskDevice失败");
        }
        //检查其他手机测试任务是否完成  如果已经完成则把testTask表改为已完成
        List<TestTaskDevice> notFinishedTestTaskDevices = testTaskDeviceMapper.findNotFinishedTestTaskDevices(testTaskId);
        if(notFinishedTestTaskDevices == null || notFinishedTestTaskDevices.size() == 0){
            //所有手机的测试任务都完成了 保存测试任务的完成时间、状态
            TestTask testTask = new TestTask();
            testTask.setTestTaskId(testTaskId);
            testTask.setStatus(TaskStatus.FINISHED.getStatus());
            testTask.setEndTime(new Date());
            testTaskService.updateTestTask(testTask);
            //统计测试报告通过 失败 跳过 数
            countReportPassFailSkip(testTaskId);
        }
    }

    private void countReportPassFailSkip(Integer testTaskId) {
        testTaskReportService.countReportPassFailSkip(testTaskId);
    }

    public void saveTestTaskDevice(String deviceId, Integer testTaskId) {
        int row = testTaskDeviceMapper.saveTestTaskDevice(deviceId, testTaskId);
        if(row != 1){
            throw new BusinessException("保存testTaskDevice失败");
        }
    }
}
