package com.fgnb.controller;

import com.fgnb.domain.Device;
import com.fgnb.enums.DeviceType;
import com.fgnb.service.DeviceService;
import com.fgnb.vo.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by jiangyitao.
 */
@RestController
@RequestMapping("/device")
@Slf4j
public class DeviceController {

    @Autowired
    private DeviceService deviceService;

    /**
     * 根据项目Id 设备状态 查询设备列表
     * @param deviceStatus
     * @return
     */
    @GetMapping("/getDeviceListByProjectIdAndDeviceStatus")
    public Response getDeviceListByProjectIdAndDeviceStatus(Integer projectId,Integer deviceStatus){
        if(projectId == null || deviceStatus == null){
            return Response.fail("非法参数");
        }
        return Response.success(deviceService.getDeviceListByProjectIdAndDeviceStatus(projectId,deviceStatus));
    }

    //通过手机id查询 1.agent查询手机是否是首次接入  2.设备控制页面获取设备信息
    @GetMapping("/findById")
    public Response findById(String deviceId){
        if(StringUtils.isEmpty(deviceId)){
            return Response.fail("设备id不能为空");
        }
        return Response.success(deviceService.findById(deviceId));
    }

    //保存手机状态 for agent
    @PostMapping("/save")
    public Response save(@Validated @RequestBody Device device){
        deviceService.save(device);
        return Response.success("保存成功");
    }

    /**
     * 获取设备类型
     */
    @GetMapping("/types")
    public Response getDeviceTypes(){
        List<Map> deviceTypes = new ArrayList();

        for(DeviceType deviceType: DeviceType.values()){
            Map deviceTypeMap = new HashMap();
            deviceTypeMap.put("deviceTypeId",deviceType.getType());
            deviceTypeMap.put("deviceTypeName",deviceType.getName());
            deviceTypes.add(deviceTypeMap);
        }

        return Response.success(deviceTypes);

    }
    /**
     * 动态查询手机列表
     * @param device
     * @return
     */
    @PostMapping("/list")
    public Response list(@RequestBody Device device){
        return Response.success(deviceService.list(device));
    }

    /**
     * 开始控制手机
     * @param deviceId
     * @return
     */
    @GetMapping("/start")
    public Response start(String deviceId){
        if(StringUtils.isEmpty(deviceId)){
            return Response.fail("设备id不能为空");
        }
        deviceService.start(deviceId);
        return Response.success("授权成功");
    }
}
