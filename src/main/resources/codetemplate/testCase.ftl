import macaca.client.MacacaClient;
import org.testng.annotations.Test;
import com.fgnb.actions.utils.MacacaUtil;

public class ${testClassName} {

    <#-- ${port?c} 去除数字逗号分隔 -->
    private MacacaClient client;

    <#-- 全局变量 变量名格式为g_xxx -->
    <#if globalVars?? && (globalVars?size>0)>
        <#list globalVars as globalVar>
    public static final String g_${globalVar.globalVarName} = "${globalVar.globalVarValue}";
        </#list>
    </#if>

    @Test(description = "${testTaskId}@&@${testTaskReportId}@&@${deviceId}@&@${testCaseId}@&@${testCaseName}")
    public void test() throws Exception {
        client = MacacaUtil.createMacacaClient("${deviceId}",${port?c});
        ${testMethod}
    }

    <#if methods?? && (methods?size>0)>
        <#list methods as method>
        <#-- 方法 使用","分隔参数 参数格式p_xxx -->
    public String ${method.methodName}(<#if method.methodParams?? && (method.methodParams?size>0)><#list method.methodParams as methodParam>String p_${methodParam}<#if methodParam_has_next>,</#if></#list></#if>) throws Exception {
          <#-- 基础action -->
          <#if method.className?? && method.className!=''>
          <#-- 基础action直接return -->
          return new ${method.className}(client).excute(<#if method.methodParams?? && (method.methodParams?size>0)><#list method.methodParams as methodParam>p_${methodParam}<#if methodParam_has_next>,</#if></#list></#if>);
          <#-- 非基础action -->
         <#else>
         <#if method.vars?? && (method.vars?size>0)>
             <#list method.vars as var>
                 <#list var?keys as key>
                 <#-- 局部变量，格式v_xxx -->
       String v_${key} =  <#if var[key]?? && var[key]!=''>"${var[key]}"<#else>null</#if>;
                 </#list>
             </#list>
         </#if>

         <#if method.methodSteps?? && (method.methodSteps?size>0)>
             <#list method.methodSteps as methodStep>
             <#-- 方法内的步骤 使用局部变量v_xxx赋值-->
       <#if methodStep.evaluation?? && methodStep.evaluation!=''>v_${methodStep.evaluation} = </#if>${methodStep.methodName}(<#if methodStep.methodParamValues?? && (methodStep.methodParamValues?size>0)><#list methodStep.methodParamValues as methodParamValue><#if methodParamValue?? && methodParamValue!=''><#-- 全局变量 --><#if methodParamValue?starts_with('${') && methodParamValue?ends_with('}')>g_${methodParamValue?substring(2,(methodParamValue)?length-1)}<#-- 方法参数 --><#elseif methodParamValue?starts_with('#{') && methodParamValue?ends_with('}')>p_${methodParamValue?substring(2,(methodParamValue)?length-1)}<#-- 局部变量 --><#elseif methodParamValue?starts_with('@{') && methodParamValue?ends_with('}')>v_${methodParamValue?substring(2,(methodParamValue)?length-1)}<#-- 普通字符串 --><#else>"${methodParamValue}"</#if><#else>null</#if><#if methodParamValue_has_next>,</#if></#list></#if>);
       </#list>
       </#if>

       <#if method.returnValue?? && method.returnValue!=''>
       <#-- 方法返回值 使用局部变量v_xxx返回-->
       return v_${method.returnValue};
       <#else>
       return null;
       </#if>

         </#if>
    }
        </#list>
    </#if>

}