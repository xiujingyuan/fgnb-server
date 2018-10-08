package com.fgnb.controller;

import com.fgnb.domain.TestTask;
import com.fgnb.service.TestTaskService;
import com.fgnb.vo.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * Created by jiangyitao.
 */
@RestController
@RequestMapping("/testTask")
public class TestTaskController {

    @Autowired
    private TestTaskService testTaskService;

    /**
     * 提交测试任务
     * @param testTask
     * @return
     */
    @PostMapping("/commit")
    public Response commitTestTask(@RequestBody @Valid TestTask testTask){
        testTaskService.commitTestTask(testTask);
        return Response.success("提交成功，测试完成后将生成测试报告");
    }

    /**
     * 动态查询测试任务列表
     * @param testTask
     * @return
     */
    @PostMapping("/list")
    public Response dynamicQuery(@RequestBody TestTask testTask){
        return Response.success(testTaskService.dynamicQuery(testTask));
    }
}
