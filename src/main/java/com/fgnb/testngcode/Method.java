package com.fgnb.testngcode;

import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * Created by jiangyitao.
 */
@Data
public class Method {
    //方法名
    private String methodName;
    //方法描述
    private String methodDescription;
    //方法参数
    private List<String> methodParams;
    //局部变量
    private List<Map<String,String>> vars;
    //返回值
    private String returnValue;
    //方法步骤
    private List<MethodStep> methodSteps;

    private String className;
}
