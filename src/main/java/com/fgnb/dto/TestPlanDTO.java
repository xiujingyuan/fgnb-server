package com.fgnb.dto;

import com.fgnb.domain.TestPlan;
import com.fgnb.domain.TestPlanBefore;
import com.fgnb.domain.TestPlanTestSuite;
import lombok.Data;

import java.util.List;

/**
 * Created by jiangyitao.
 */
@Data
public class TestPlanDTO extends TestPlan{
    private List<TestPlanBefore> testPlanBeforeList;
    private List<TestPlanTestSuite> testPlanTestSuites;
}
