package com.fgnb.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.fgnb.domain.Device;
import com.fgnb.enums.DeviceStatus;
import com.fgnb.exception.BusinessException;
import com.fgnb.mapper.DeviceMapper;
import com.fgnb.vo.PageVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by jiangyitao.
 */
@Service
public class DeviceService extends BaseService{
    
    @Autowired
    private DeviceMapper deviceMapper;

    public Device findById(String deviceId) {
        return deviceMapper.findById(deviceId);
    }

    public void save(Device device) {
        Device dbDevice = deviceMapper.findById(device.getDeviceId());
        int row;
        if(dbDevice == null){
            //insert
            row = deviceMapper.insert(device);
        }else{
            //update
            row = deviceMapper.update(device);
        }
        if(row!=1){
            throw new BusinessException("save失败");
        }
    }

    public PageVo list(Device device) {
        PageHelper.startPage(device.getPageIndex(),device.getCountPerPage(),"lastOnlineTime desc");
        List<Device> devices = deviceMapper.queryList(device);
        return PageVo.convert(new PageInfo(devices));
    }

    public void start(String deviceId) {
        Device device = deviceMapper.findById(deviceId);
        if(device==null){
            throw new BusinessException("数据库找不到该手机");
        }
        if(device.getStatus() == DeviceStatus.OFFLINE.getStatus()){
            throw new BusinessException("设备已离线");
        }
        if(device.getStatus() == DeviceStatus.USING.getStatus()){
            throw new BusinessException("["+device.getUserName()+"]使用中");
        }
    }

    public void setDeviceUserName(String deviceId,String userName){
        deviceMapper.setDeviceUserName(deviceId,userName);
    }

    public List<Device> getDeviceListByProjectIdAndDeviceStatus(Integer projectId, Integer deviceStatus) {
        return deviceMapper.getDeviceListByProjectIdAndDeviceStatus(projectId,deviceStatus);
    }

    public List<Device> findByDeviceIds(List<String> deviceIds){
        return deviceMapper.findByDeviceIds(deviceIds);
    }
}
