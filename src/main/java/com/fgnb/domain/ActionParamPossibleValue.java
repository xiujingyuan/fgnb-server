package com.fgnb.domain;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * Created by jiangyitao.
 */
@Data
public class ActionParamPossibleValue {
    private Integer actionParamPossibleValueId;
    @NotBlank(message = "actionParamPossibleValue不能为空")
    private String actionParamPossibleValue;
    private String description;
    @NotNull(message = "actionParamId不能为空")
    private Integer actionParamId;
}
