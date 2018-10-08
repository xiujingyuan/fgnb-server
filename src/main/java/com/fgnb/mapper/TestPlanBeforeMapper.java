package com.fgnb.mapper;

import com.fgnb.domain.TestPlanBefore;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by jiangyitao.
 */
@Mapper
public interface TestPlanBeforeMapper {

    int addTestPlanBefores(List<TestPlanBefore> testPlanBefores);

    @Delete("delete from test_plan_before where testPlanId = #{testPlanId}")
    void deleteByTestPlanId(Integer testPlanId);

    @Select("select * from test_plan_before where testPlanId = #{testPlanId}")
    List<TestPlanBefore> findByTestPlanId(Integer testPlanId);
}
