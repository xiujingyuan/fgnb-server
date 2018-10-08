package com.fgnb.mapper;

import com.fgnb.domain.TestPlanTestSuite;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Created by jiangyitao.
 */
@Mapper
public interface TestPlanTestSuiteMapper {

    int addTestSuites(List<TestPlanTestSuite> testPlanTestSuites);

    @Delete("delete from test_plan_test_suite where testPlanId = #{testPlanId}")
    void deleteByTestPlanId(Integer testPlanId);
}
