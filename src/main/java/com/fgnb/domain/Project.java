package com.fgnb.domain;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * Created by jiangyitao.
 */
@Data
public class Project extends BaseDomain{

    private Integer projectId;
    @NotBlank(message = "项目名不能为空")
    private String projectName;
    private String description;
    /** 项目类型 1.android 2.ios */
    @NotNull(message = "项目类型不能为空")
    private Integer projectType;
}
