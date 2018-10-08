package com.fgnb.domain;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * Created by jiangyitao.
 */
@Data
public class ActionParam {
    private Integer actionParamId;
    @NotBlank(message = "actionParamName不能为空")
    private String actionParamName;
    private String description;
    private Integer actionId;

    private List<ActionParamPossibleValue> actionParamPossibleValues;
}
