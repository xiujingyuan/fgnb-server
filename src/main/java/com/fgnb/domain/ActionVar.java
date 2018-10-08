package com.fgnb.domain;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * Created by jiangyitao.
 */
@Data
public class ActionVar {
    private Integer actionVarId;
    @NotBlank(message = "actionVarName不能为空")
    private String actionVarName;
    private String actionVarValue;
    private String description;
    private Integer actionId;
}
