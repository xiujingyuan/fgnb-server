package com.fgnb.controller;

import com.fgnb.service.TestTaskDeviceService;
import com.fgnb.vo.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by jiangyitao.
 */
@RestController
@RequestMapping("/testTaskDevice")
public class TestTaskDeviceController {


    @Autowired
    private TestTaskDeviceService testTaskDeviceService;
    /**
     * 开始测试任务
     * @return
     */
    @GetMapping("/start")
    public Response deviceTaskStart(Integer testTaskId,String deviceId){
        if(testTaskId == null || StringUtils.isEmpty(deviceId)){
            return Response.fail("任务id或设备id不能为空");
        }
        testTaskDeviceService.deviceTaskStart(testTaskId,deviceId);
        return Response.success("ok");
    }

    @GetMapping("/finish")
    public Response deviceTaskFinish(Integer testTaskId,String deviceId){
        if(testTaskId == null || StringUtils.isEmpty(deviceId)){
            return Response.fail("任务id或设备id不能为空");
        }
        testTaskDeviceService.deviceTaskFinish(testTaskId,deviceId);
        return Response.success("ok");
    }


}
