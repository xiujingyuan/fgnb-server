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
public class TestSuite extends BaseDomain{
    private Integer testSuiteId;
    @NotBlank(message = "testSuiteName不能为空")
    private String testSuiteName;
    private String description;
    @NotNull(message = "projectId不能为空")
    private Integer projectId;

    @NotEmpty(message = "测试用例不能为空")
    private List<Integer> testSuiteTestCaseIds;
}
