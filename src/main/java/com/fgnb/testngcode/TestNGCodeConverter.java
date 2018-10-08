package com.fgnb.testngcode;

import com.fgnb.domain.*;
import com.fgnb.dto.ActionDTO;

import java.util.*;

/**
 * Created by jiangyitao.
 */
public class TestNGCodeConverter {

    private Map<Integer,Method> methodMap = new HashMap();

    public String convertBeforeSuite(String testClassName,ActionDTO actionDTO,String ftlFileName) throws Exception{
        return convertDebugAction(testClassName,actionDTO,ftlFileName);
    }

    public String convertTestCase(String testClassName,ActionDTO actionDTO,String ftlFileName) throws Exception{
        return convertDebugAction(testClassName,actionDTO,ftlFileName);
    }

    /**
     * 调试action转为testng代码
     * @param action
     * @return
     */
    public String convertDebugAction(String testClassName,ActionDTO action,String ftlFileName) throws Exception{
        //递归遍历出action里所有的action并封装到Method里
        walk(action);
        List<Method> methodList = new ArrayList<>(methodMap.values());

        Map<String,Object> dataModel = new HashMap();
        dataModel.put("globalVars",action.getGlobalVars());
        dataModel.put("methods",methodList);
        dataModel.put("testClassName",testClassName);
        dataModel.put("deviceId",action.getDeviceId());
        dataModel.put("port",action.getUiautomatorServerPort());

        dataModel.put("testTaskId",action.getTestTaskId());
        dataModel.put("testTaskReportId",action.getTestTaskReportId());
        dataModel.put("testCaseId",action.getActionId());
        dataModel.put("testCaseName",action.getActionName());

        //testng @Test注解下调用的方法
        StringBuilder testMethod = new StringBuilder("m_"+action.getActionId()+"(");
        List<ActionParam> actionParams = action.getActionParams();
        //如果有参数 则都传入null
        if(actionParams != null){
            int paramSize = actionParams.size();
            for (int i = 0; i < paramSize; i++) {
                testMethod.append("null");
                if(i!=paramSize-1){
                    testMethod.append(",");
                }
            }
        }
        testMethod.append(");");
        dataModel.put("testMethod",testMethod.toString());

        return FreemarkerUtil.process( "/codetemplate",ftlFileName, dataModel);
    }

    /**
     * 递归遍历出action里所有的action并封装到Method里
     * @param action
     * @return
     */
    private void walk(Action action) throws Exception{
        Integer actionId = action.getActionId();
        Method method = methodMap.get(actionId);
        if(method == null){
            method = new Method();
            //className className不为空 意味着是基础的action
            method.setClassName(action.getClassName());
            //方法名 m_${actionId}
            method.setMethodName("m_"+actionId);
            //方法描述
            method.setMethodDescription(action.getActionName());
            //方法参数
            List<String> methodParams = new ArrayList<>();
            if(action.getActionParams()!=null){
                for(ActionParam actionParam:action.getActionParams()){
                    methodParams.add(actionParam.getActionParamName());
                }
            }
            method.setMethodParams(methodParams);
            //局部变量
            List<Map<String,String>> vars = new ArrayList<>();
            for(ActionVar actionVar:action.getActionVars()){
                Map var = new HashMap();
                var.put(actionVar.getActionVarName(),actionVar.getActionVarValue());
                vars.add(var);
            }
            method.setVars(vars);
            //返回值
            method.setReturnValue(action.getReturnValue());
            //步骤里的action
            List<ActionStep> actionSteps = action.getActionSteps();
            List<MethodStep> methodSteps = new ArrayList<>();
            for(ActionStep actionStep:actionSteps){
                //步骤
                MethodStep methodStep = new MethodStep();
                //调用方法名
                methodStep.setMethodName("m_"+actionStep.getStepActionId());
                //步骤名
                methodStep.setMethodStepName(actionStep.getActionStepName());
                //步骤赋值
                methodStep.setEvaluation(actionStep.getEvaluation());
                //步骤传入的参数
                List<ActionStepParamValue> actionStepParamValues = actionStep.getActionStepParamValues();
                List<String> methodParamValues = new ArrayList<>();
                for(ActionStepParamValue actionStepParamValue:actionStepParamValues){
                    methodParamValues.add(actionStepParamValue.getActionParamValue());
                }
                methodStep.setMethodParamValues(methodParamValues);
                methodSteps.add(methodStep);
                //步骤里的action
                Action stepAction = actionStep.getAction();
                walk(stepAction);
            }
            method.setMethodSteps(methodSteps);
            methodMap.put(actionId,method);
        }
    }

}
