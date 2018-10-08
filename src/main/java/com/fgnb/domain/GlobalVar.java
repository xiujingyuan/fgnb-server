package com.fgnb.domain;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * Created by jiangyitao.
 */
@Data
public class GlobalVar extends BaseDomain{
    private Integer globalVarId;
    @NotBlank(message = "变量名不能为空")
    private String globalVarName;
    @NotBlank(message = "变量值不能为空")
    private String globalVarValue;
    private String description;
    @NotNull(message = "项目id不能为空")
    private Integer projectId;
}
