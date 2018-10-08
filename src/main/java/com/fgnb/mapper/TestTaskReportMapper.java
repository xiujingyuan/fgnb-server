package com.fgnb.mapper;

import com.fgnb.domain.TestTaskReport;
import com.fgnb.vo.TestTaskReportVo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * Created by jiangyitao.
 */
@Mapper
public interface TestTaskReportMapper {
    int addTestTaskReport(TestTaskReport testTaskReport);

    TestTaskReportVo getReport(Integer testTaskId);

    @Select("select * from test_task_report where testTaskId = #{testTaskId}")
    TestTaskReport findByTestTaskId(Integer testTaskId);

    @Insert("update test_task_report set passCount = #{passCount},failCount = #{failCount},skipCount = #{skipCount}  where testTaskReportId = #{testTaskReportId}")
    int saveReportPassFailSkipInfo(@Param("passCount") int passCount,
                                   @Param("failCount") int failCount,
                                   @Param("skipCount")int skipCount,
                                   @Param("testTaskReportId") Integer testTaskReportId);
}
