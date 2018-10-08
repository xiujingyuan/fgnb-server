package com.fgnb.domain;

import lombok.Data;


/**
 * Created by jiangyitao.
 */
@Data
public class TestTaskReport {
    private Integer testTaskReportId;
    private Integer testTaskId;

    private Integer passCount;
    private Integer failCount;
    private Integer skipCount;
}
