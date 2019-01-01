package com.fgnb.domain;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * Created by jiangyitao.
 */
@Data
public class Page extends BaseDomain{

    private Integer pageId;
    @NotBlank(message = "page名不能为空")
    private String pageName;
    @NotNull(message = "所属项目不能为空")
    private Integer projectId;
    @NotNull(message = "分类不能为空")
    private Integer categoryId;
    private String description;

    private String imgUrl;
    private Integer imgHeight;
    private Integer imgWidth;
    private String windowHierarchyJson;
    private String deviceId;
}
