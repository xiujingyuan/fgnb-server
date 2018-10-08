package com.fgnb.domain;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * Created by jiangyitao.
 */
@Data
public class TestPlanBefore {
    private Integer testPlanBeforeId;
    private Integer testPlanId;
    @NotNull(message = "actionId不能为空")
    private Integer actionId;
    private String actionName;
    /** 1:before suite 2.before testcase */
    @NotNull(message = "type不能为空")
    private Integer type;
}
