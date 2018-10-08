package com.fgnb.testngcode;

import lombok.Data;

import java.util.List;

/**
 * Created by jiangyitao.
 */
@Data
public class MethodStep {
    //赋值
    private String evaluation;
    //调用方法名
    private String methodName;
    //步骤名
    private String methodStepName;
    //调用方法传递的参数值
    private List<String> methodParamValues;
}
