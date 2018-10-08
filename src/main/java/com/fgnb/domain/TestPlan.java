package com.fgnb.domain;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Created by jiangyitao.
 */
@Data
public class TestPlan extends BaseDomain{
    private Integer testPlanId;
    @NotNull(message = "projectId不能为空")
    private Integer projectId;
    @NotBlank(message = "testPlanName不能为空")
    private String testPlanName;
    private String description;

    private Integer testPlanBeforeSuiteActionId;
    private String testPlanBeforeSuiteActionName;
    private Integer testPlanBeforeMethodActionId;
    private String testPlanBeforeMethodActionName;

    @NotEmpty(message = "testSuite不能为空")
    private List<Integer> testPlanTestSuiteIds;
}
