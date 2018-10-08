package com.fgnb.service;

import com.fgnb.domain.TestTaskReport;
import com.fgnb.domain.TestTaskReportTestCaseRecord;
import com.fgnb.enums.TestCaseStatus;
import com.fgnb.exception.BusinessException;
import com.fgnb.mapper.TestTaskReportMapper;
import com.fgnb.mapper.TestTaskReportTestCaseRecordMapper;
import com.fgnb.vo.TestTaskReportVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by jiangyitao.
 */
@Service
public class TestTaskReportService {

    @Autowired
    private TestTaskReportMapper testTaskReportMapper;
    @Autowired
    private TestTaskReportTestCaseRecordMapper testTaskReportTestCaseRecordMapper;

    /**
     * 添加测试任务报告
     * @param testTaskId
     * @return
     */
    public TestTaskReport addTestTaskReport(Integer testTaskId){
        TestTaskReport testTaskReport = new TestTaskReport();
        testTaskReport.setTestTaskId(testTaskId);
        int row = testTaskReportMapper.addTestTaskReport(testTaskReport);
        if(row != 1){
            throw new BusinessException("添加测试任务报告失败");
        }
        return testTaskReport;
    }

    public TestTaskReportVo getReport(Integer testTaskId) {
        TestTaskReportVo report = testTaskReportMapper.getReport(testTaskId);
        if(report == null){
            throw new BusinessException("暂无数据");
        }
        return report;
    }

    /**
     * 统计pass fail skip个数
     * @param testTaskId
     */
    public void countReportPassFailSkip(Integer testTaskId) {
        TestTaskReport testTaskReport = testTaskReportMapper.findByTestTaskId(testTaskId);
        List<TestTaskReportTestCaseRecord> records = testTaskReportTestCaseRecordMapper.findByTestTaskReportId(testTaskReport.getTestTaskReportId());
        int passCount = 0;
        int failCount = 0;
        int skipCount = 0;
        if(records != null){
            for(TestTaskReportTestCaseRecord record:records){
                if(record.getStatus() == TestCaseStatus.SUCCESS.getStatus()){
                    passCount ++;
                }else if(record.getStatus() == TestCaseStatus.FAIL.getStatus()){
                    failCount ++;
                }else if(record.getStatus() == TestCaseStatus.SKIP.getStatus()){
                    skipCount++;
                }
            }
        }
        int row = testTaskReportMapper.saveReportPassFailSkipInfo(passCount,failCount,skipCount,testTaskReport.getTestTaskReportId());
        if(row != 1){
            throw new BusinessException("保存测试报告统计数据失败");
        }
    }
}
