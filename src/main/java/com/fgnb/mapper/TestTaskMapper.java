package com.fgnb.mapper;

import com.fgnb.domain.TestTask;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by jiangyitao.
 */
@Mapper
public interface TestTaskMapper {


    @Select("select * from test_task where projectId = #{projectId} and testTaskName = #{testTaskName}")
    TestTask findTestTaskByProjectIdAndTestTaskName(TestTask testTask);

    int addTestTask(TestTask testTask);

    int updateTestTask(TestTask testTask);

    List<TestTask> dynamicQuery(TestTask testTask);

}
