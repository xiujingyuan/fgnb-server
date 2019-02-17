package com.fgnb.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fgnb.agent.AgentApi;
import com.fgnb.mapper.DeviceMapper;
import com.fgnb.vo.AgentVo;
import com.fgnb.vo.Response;
import de.codecentric.boot.admin.server.domain.values.StatusInfo;
import de.codecentric.boot.admin.server.services.InstanceRegistry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jiangyitao.
 */
@RestController
@RequestMapping("/agent")
@Slf4j
public class AgentController {

    @Autowired
    private InstanceRegistry instanceRegistry;

    @Autowired
    private DeviceMapper deviceMapper;

    @Autowired
    private AgentApi agentApi;

    @GetMapping("/listOfOnline")
    public Response listOfOnline(){

        List<AgentVo> agents = new ArrayList();
        instanceRegistry.getInstances().collectList().block().forEach(instance -> {
            if(StatusInfo.STATUS_UP.equals(instance.getStatusInfo().getStatus())){
                AgentVo agentVo = new AgentVo();
                String url = instance.getRegistration().getServiceUrl();//http://xx.xx.xx.x:10002/
                String[] host = url.split("//")[1].split(":");
                String agentIp = host[0];
                String agentPort = host[1].substring(0,host[1].length()-1);
                agentVo.setAgentIp(agentIp);
                agentVo.setAgentPort(agentPort);
                agentVo.setDevices(deviceMapper.findNotOfflineDevicesByAgentIp(agentIp));

                try {
                    //deviceType 1:chrome
                    JSONObject resp = JSON.parseObject(agentApi.getWebDriverPort(agentIp,agentPort, 1).asString());
                    if("1".equals(resp.getString("status"))){
                        int chromeDriverPort = resp.getJSONObject("data").getIntValue("port");
                        if(chromeDriverPort > 0){
                            agentVo.setChromeDriverPort(chromeDriverPort);
                        }
                    }
                }catch (Exception e){
                    log.error("获取ChromeDriverPort失败",e);
                }
                agents.add(agentVo);
            }
        });
        return Response.success(agents);

    }
}
