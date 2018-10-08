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

    @NotBlank(message = "图片地址不能为空")
    private String imgUrl;
    @NotNull(message = "图片高度不能为空")
    private Integer imgHeight;
    @NotNull(message = "图片宽度不能为空")
    private Integer imgWidth;
    @NotBlank(message = "布局json不能为空")
    private String windowHierarchyJson;
    private String deviceId;
}
