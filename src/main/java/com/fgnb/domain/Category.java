package com.fgnb.domain;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * Created by jiangyitao.
 */
@Data
public class Category {

    private Integer categoryId;
    @NotBlank(message = "分类名不能为空")
    private String categoryName;
    @NotNull(message = "分类类型不能为空")
    private Integer categoryType;
    @NotNull(message = "项目Id不能为空")
    private Integer projectId;
    private Date createTime;
}
