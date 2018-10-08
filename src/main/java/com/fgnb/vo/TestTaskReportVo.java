package com.fgnb.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * Created by jiangyitao.
 */
@Data
public class TestTaskReportVo {
    private String projectName;
    private Integer projectType;

    private String testTaskName;
    private String creatorName;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date startTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date endTime;

    private Integer passCount;
    private Integer failCount;
    private Integer skipCount;

    private List<DeviceReportInfo> deviceReportInfos;

    @Data
    public static class DeviceReportInfo {
        private String deviceId;
        private List<DeviceTestCaseInfo> deviceTestCaseInfos;
    }
    @Data
    public static class DeviceTestCaseInfo {
        private String testCaseId;
        private String testCaseName;
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
        private Date startTime;
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
        private Date endTime;
        private String imgUrl;
        private String errorInfo;
        private String videoUrl;
        private Integer status;
    }

}
