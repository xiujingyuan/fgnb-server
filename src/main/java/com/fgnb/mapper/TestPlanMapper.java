package com.fgnb.mapper;

import com.fgnb.domain.TestPlan;
import com.fgnb.dto.TestPlanDTO;
import com.fgnb.vo.testplan.TestPlanInfoVo;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import javax.validation.Valid;
import java.util.List;

/**
 * Created by jiangyitao.
 */
@Mapper
public interface TestPlanMapper {

    int addTestPlan(TestPlan testPlan);

    List<TestPlan> findTestPlansByProjectId(Integer projectId);

    @Delete("delete from test_plan where testPlanId = #{testPlanId}")
    int deleteTestPlan(Integer testPlanId);

    TestPlanDTO findTestPlanDetailInfoByTestPlanId(Integer testPlanId);

    int update(@Valid TestPlan testPlan);

    TestPlanInfoVo findTestPlanInfoVoByTestPlanId(Integer testPlanId);

    List<Integer> findTestCaseIdsByTestPlanId(Integer testPlanId);
}
