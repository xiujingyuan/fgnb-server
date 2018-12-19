package com.fgnb.mapper;

import com.fgnb.domain.Device;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * Created by jiangyitao.
 */
@Mapper
public interface DeviceMapper {

    @Select("select * from device where deviceId = #{deviceId}")
    Device findById(String deviceId);

    @Select("select * from device where agentIp = #{agentIp} and status != 0")
    List<Device> findNotOfflineDevicesByAgentIp(String agentIp);

    @Insert("insert into device " +
            "(" +
            "deviceId,deviceName,phoneIp,agentIp,systemVersion,apiLevel," +
            "cpuAbi,cpuInfo,memSize,resolution,imgUrl,deviceType,status," +
            "stfStatus,macacaStatus,lastOnlineTime,lastOfflineTime," +
            "userName,createTime" +
            ") " +
            "values" +
            "(" +
            "#{deviceId},#{deviceName},#{phoneIp},#{agentIp},#{systemVersion},#{apiLevel}," +
            "#{cpuAbi},#{cpuInfo},#{memSize},#{resolution},#{imgUrl},#{deviceType},#{status}," +
            "#{stfStatus},#{macacaStatus},#{lastOnlineTime},#{lastOfflineTime},#{userName},#{createTime}" +
            ")")
    int insert(Device device);

    int update(Device device);

    List<Device> queryList(Device device);

    @Update("update device set userName = #{userName},status = 2 where deviceId = #{deviceId}")
    void setDeviceUserName(@Param("deviceId") String deviceId,@Param("userName") String userName);

    @Select("select *  from device where status = #{deviceStatus} and deviceType = (select projectType from project where projectId = #{projectId})")
    List<Device> getDeviceListByProjectIdAndDeviceStatus(@Param("projectId") Integer projectId, @Param("deviceStatus") Integer deviceStatus);

    List<Device> findByDeviceIds(List<String> deviceIds);
}
