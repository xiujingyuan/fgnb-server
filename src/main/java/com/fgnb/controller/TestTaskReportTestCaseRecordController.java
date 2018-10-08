package com.fgnb.controller;

import com.fgnb.domain.TestTaskReportTestCaseRecord;
import com.fgnb.service.TestTaskReportTestCaseRecordService;
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
@RequestMapping("/testTaskReportTestCaseRecord")
public class TestTaskReportTestCaseRecordController {

    @Autowired
    private TestTaskReportTestCaseRecordService testTaskReportTestCaseRecordService;

    /**
     * 添加测试报告的测试用例记录  开始测试xxx用例
     * @param record
     * @return
     */
    @PostMapping("/add")
    public Response addRecord(@RequestBody @Valid TestTaskReportTestCaseRecord record){
        return Response.success(testTaskReportTestCaseRecordService.addRecord(record));
    }

    /**
     * 更新测试报告的测试用例记录  测试失败/跳过/成功
     * status  ->  0:fail 1:success 2:skip
     * @param record
     * @return
     */
    @PostMapping("/update")
    public Response updateRecord(@RequestBody TestTaskReportTestCaseRecord record){
        if(record.getTestTaskReportTestCaseRecordId() == null){
            return Response.fail("测试报告用例记录不能为空");
        }
        testTaskReportTestCaseRecordService.updateRecord(record);
        return Response.success("更新成功");
    }
}
