package com.fgnb.vo;

import com.fgnb.domain.Device;
import lombok.Data;

import java.util.List;

/**
 * Created by jiangyitao.
 */
@Data
public class AgentVo {
    private String agentIp;
    private String agentPort;
    private Integer chromeDriverPort;
    private List<Device> devices;
}
