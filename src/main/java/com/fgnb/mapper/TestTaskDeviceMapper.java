package com.fgnb.mapper;

import com.fgnb.domain.TestTaskDevice;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * Created by jiangyitao.
 */
@Mapper
public interface TestTaskDeviceMapper {
    @Select("select * from test_task_device where testTaskId = #{testTaskId} and deviceId = #{deviceId}")
    TestTaskDevice findByTestTaskIdAndDeviceId(@Param("testTaskId") Integer testTaskId,@Param("deviceId") String deviceId);

    int update(TestTaskDevice testTaskDevice);

    //没有完成测试任务的设备
    @Select("select * from test_task_device where testTaskId = #{testTaskId} and (status is null or status = 0)")
    List<TestTaskDevice> findNotFinishedTestTaskDevices(Integer testTaskId);

    @Insert("insert into test_task_device(deviceId,testTaskId) values(#{deviceId},#{testTaskId})")
    int saveTestTaskDevice(@Param("deviceId") String deviceId, @Param("testTaskId")Integer testTaskId);
}
