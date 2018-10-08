package com.fgnb.service;

import com.fgnb.domain.TestTaskReportTestCaseRecord;
import com.fgnb.exception.BusinessException;
import com.fgnb.mapper.TestTaskReportTestCaseRecordMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created by jiangyitao.
 */
@Service
public class TestTaskReportTestCaseRecordService {

    @Autowired
    private TestTaskReportTestCaseRecordMapper testTaskReportTestCaseRecordMapper;

    public TestTaskReportTestCaseRecord addRecord(TestTaskReportTestCaseRecord record) {
        //第一次添加 意味着用例开始测试
        record.setStartTime(new Date());
        int row = testTaskReportTestCaseRecordMapper.addRecord(record);
        if(row != 1){
            throw new BusinessException("添加测试报告测试用例记录失败");
        }
        return record;
    }

    public void updateRecord(TestTaskReportTestCaseRecord record) {
        //更新record 意味着用例测试结束
        record.setEndTime(new Date());
        int row = testTaskReportTestCaseRecordMapper.updateRecord(record);
        if(row != 1){
            throw new BusinessException("更新测试报告测试用例记录失败");
        }
    }
}
