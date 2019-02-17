package com.fgnb.agent;

import com.alibaba.fastjson.JSONObject;
import io.restassured.http.ContentType;
import org.springframework.stereotype.Component;
import io.restassured.response.Response;

import static io.restassured.RestAssured.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by jiangyitao.
 */
@Component
public class AgentApi {
    /**
     * 检查手机能否调试action
     * @param agentIp
     * @param uiautomator2Port
     * @return
     */
    public Response checkDeviceCanDebugAction(String agentIp,int agentPort,int uiautomator2Port){
        return get("http://" + agentIp + ":" + agentPort + "/debug/checkDeviceCanDebugAction?uiautomator2Port=" + uiautomator2Port);
    }

    /**
     * 调试action
     * @param agentIp
     * @param code
     * @return
     */
    public Response debugAction(String agentIp,int agentPort,String testClassName,String code){
        Map body = new HashMap();
        body.put("testNGCode",code);
        body.put("testClassName",testClassName);
        return given()
                .contentType(ContentType.JSON)
                .body(body)
                .post("http://" + agentIp + ":" + agentPort + "/debug/debugAction");
    }

    /**
     * 打开手机的uiautomatorServer
     * @param agentIp
     * @return
     */
    public Response openUiAutomatorServer(String agentIp,int agentPort,String deviceId){
        return get("http://" + agentIp + ":" + agentPort + "/device/openUiAutomatorServer?deviceId=" + deviceId);
    }

    /**
     * 提交测试任务
     * @return
     */
    public Response commitTestTask(String agentIp,int agentPort,String deviceId,Map<String,String> codes){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("deviceId",deviceId);
        jsonObject.put("codes",codes);
        return given().contentType(ContentType.JSON).body(jsonObject).post("http://"+agentIp+":"+agentPort+"/task/commit");
    }

    /**
     * 获取webdriver端口
     * @param agentIp
     * @param driverType
     * @return
     */
    public Response getWebDriverPort(String agentIp,String agentPort,Integer driverType){
        return get("http://"+agentIp+":"+agentPort+"/selenium/getPort?driverType="+driverType);
    }
}
