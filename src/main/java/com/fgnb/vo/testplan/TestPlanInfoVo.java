package com.fgnb.vo.testplan;

import com.fgnb.domain.TestPlanBefore;
import lombok.Data;

import java.util.List;

/**
 * Created by jiangyitao.
 */
@Data
public class TestPlanInfoVo {
    private Integer testPlanId;
    private String testPlanName;
    private String description;
    private String testPlanBeforeSuiteActionName;
    private String testPlanBeforeMethodActionName;
    private List<TestPlanBefore> testPlanBeforeList;
    private List<Node> testCasesTreeData;
}
