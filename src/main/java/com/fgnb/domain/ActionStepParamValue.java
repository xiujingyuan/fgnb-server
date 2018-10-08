package com.fgnb.domain;

import lombok.Data;


/**
 * Created by jiangyitao.
 */
@Data
public class ActionStepParamValue {
    private Integer actionStepParamValueId;
    private Integer actionStepId;

    //actionParam-start
    private Integer actionParamId;
    private String actionParamName;
    private String description;
    //actionParam-end

    private String actionParamValue;
}
