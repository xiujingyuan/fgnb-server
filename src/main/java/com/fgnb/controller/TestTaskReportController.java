package com.fgnb.controller;

import com.fgnb.service.TestTaskReportService;
import com.fgnb.vo.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by jiangyitao.
 */
@RestController
@RequestMapping("/testTaskReport")
public class TestTaskReportController {

    @Autowired
    private TestTaskReportService testTaskReportService;

    /**
     * 获取测试报告内容
     * @param testTaskId
     * @return
     */
    @GetMapping("/getReport")
    public Response getReport(Integer testTaskId){
        if(testTaskId == null){
            return Response.fail("testTaskId不能为空");
        }
        return Response.success(testTaskReportService.getReport(testTaskId));
    }
}
