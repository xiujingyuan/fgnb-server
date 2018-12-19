package com.fgnb.controller;

import com.fgnb.mapper.DeviceMapper;
import com.fgnb.vo.AgentVo;
import com.fgnb.vo.Response;
import de.codecentric.boot.admin.server.domain.values.StatusInfo;
import de.codecentric.boot.admin.server.services.InstanceRegistry;
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
public class AgentController {

    @Autowired
    private InstanceRegistry instanceRegistry;

    @Autowired
    private DeviceMapper deviceMapper;

    @GetMapping("/listOfOnline")
    public Response listOfOnline(){

        List<AgentVo> agents = new ArrayList();
        instanceRegistry.getInstances().collectList().block().forEach(instance -> {
            if(StatusInfo.STATUS_UP.equals(instance.getStatusInfo().getStatus())){
                AgentVo agentVo = new AgentVo();
                String url = instance.getRegistration().getServiceUrl();//http://xx.xx.xx.x:10002/
                String[] host = url.split("//")[1].split(":");
                agentVo.setAgentIp(host[0]);
                agentVo.setAgentPort(host[1].substring(0,host[1].length()-1));
                agentVo.setDevices(deviceMapper.findNotOfflineDevicesByAgentIp(host[0]));
                agents.add(agentVo);
            }
        });
        return Response.success(agents);

    }
}
