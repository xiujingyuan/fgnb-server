package com.fgnb.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * Created by jiangyitao.
 */
@Data
public class TestTaskDevice {

    private Integer testTaskDeviceId;

    @NotNull(message = "测试任务不能为空")
    private Integer testTaskId;
    @NotBlank(message = "设备id不能为空")
    private String deviceId;

    /** 0:进行中 1:完成 2:错误 */
    private Integer status;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;
}
