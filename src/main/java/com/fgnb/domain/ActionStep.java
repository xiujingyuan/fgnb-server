package com.fgnb.domain;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Created by jiangyitao.
 */
@Data
public class ActionStep {
    private Integer actionStepId;
    private Integer actionId;

    @NotNull(message = "步骤的actionId不能为空")
    private Integer stepActionId;
    private String stepActionName;

    @NotBlank(message = "步骤名不能为空")
    private String actionStepName;
    //赋值
    private String evaluation;
    //步骤号
    @NotNull(message = "步骤号不能为空")
    private Integer actionStepNumber;

    @Valid
    List<ActionStepParamValue> actionStepParamValues;

    private Action action;

}
