package com.fgnb.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * Created by jiangyitao.
 */
@Data
public class TestTaskReportTestCaseRecord {
    private Integer testTaskReportTestCaseRecordId;

    @NotNull(message = "测试任务报告id不能为空")
    private Integer testTaskReportId;

    @NotBlank(message = "设备id不能为空")
    private String deviceId;
    @NotBlank(message = "测试用例名不能为空")
    private String testCaseName;
    @NotNull(message = "测试用例id不能为空")
    private Integer testCaseId;

    //开始时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date startTime;
    //结束时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date endTime;

    private String imgUrl;
    private String errorInfo;

    private String videoUrl;

    //0:fail 1:success 2:skip
    private Integer status;
}
