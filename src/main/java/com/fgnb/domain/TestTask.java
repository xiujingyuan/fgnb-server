package com.fgnb.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

/**
 * Created by jiangyitao.
 */
@Data
public class TestTask {
    private Integer testTaskId;

    @NotNull(message = "项目id不能为空")
    private Integer projectId;
    @NotNull(message = "测试计划不能为空")
    private Integer testPlanId;
    private String testPlanName;

    @NotBlank(message = "测试任务名不能为空")
    private String testTaskName;
    private String description;

    @NotEmpty(message = "设备不能为空")
    private List<String> testTaskDeviceIds;

    @NotNull(message = "用例分发策划不能为空")
    private Integer runMode;

    /** 0:进行中 1:完成 2:错误 */
    private Integer status;

    private Integer creatorUid;
    private String creatorName;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;

    private Integer pageIndex;
    private Integer countPerPage;

}
