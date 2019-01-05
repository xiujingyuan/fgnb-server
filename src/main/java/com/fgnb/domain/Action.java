package com.fgnb.domain;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Created by jiangyitao.
 */
@Data
public class Action extends BaseDomain{

    private Integer actionId;
    @NotBlank(message = "actionName不能为空")
    private String actionName;
    private String description;

    private Integer projectId;
    private Integer pageId;

    //分类
    private Integer categoryId;
    private String categoryName;

    //1.基础action  2.自己封装的action 3.testcase 4.testPlanBefore
    @NotNull(message = "actionType不能为空")
    private Integer actionType;

    private Integer projectType;

    private String className;

    //返回值
    private String returnValue;

    //局部变量
    @Valid
    private List<ActionVar> actionVars;
    //方法参数
    @Valid
    private List<ActionParam> actionParams;
    //方法步骤
    @Valid
    @NotEmpty(message = "步骤不能为空")
    private List<ActionStep> actionSteps;
    //全局变量
    private List<GlobalVar> globalVars;
}
