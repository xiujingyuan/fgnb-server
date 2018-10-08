package com.fgnb.mapper;

import com.fgnb.domain.TestTaskReportTestCaseRecord;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by jiangyitao.
 */
@Mapper
public interface TestTaskReportTestCaseRecordMapper {
    int addRecord(TestTaskReportTestCaseRecord record);

    int updateRecord(TestTaskReportTestCaseRecord record);

    @Select("select * from test_task_report_test_case_record where testTaskReportId = #{testTaskReportId}")
    List<TestTaskReportTestCaseRecord> findByTestTaskReportId(Integer testTaskReportId);
}
