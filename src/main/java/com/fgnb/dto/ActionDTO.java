package com.fgnb.dto;

import com.fgnb.domain.Action;
import lombok.Data;

/**
 * Created by jiangyitao.
 */
@Data
public class ActionDTO extends Action{
    //调试Action - start
    private String agentIp;
    private String deviceId;
    private Integer uiautomatorServerPort;
    //调试Action - end

    //生成测试用例 - start
    private Integer testTaskId;
    private Integer testTaskReportId;
    private Integer testCaseId;
    private String testCaseName;
    //生成测试用例 - end
}
