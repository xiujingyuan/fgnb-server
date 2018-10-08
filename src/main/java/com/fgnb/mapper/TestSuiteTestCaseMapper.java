package com.fgnb.mapper;

import com.fgnb.domain.TestSuiteTestCase;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Created by jiangyitao.
 */
@Mapper
public interface TestSuiteTestCaseMapper {
    int add(List<TestSuiteTestCase> testSuiteTestCases);

    @Delete("delete from test_suite_test_case where testSuiteId = #{testSuiteId}")
    void deleteByTestSuiteId(Integer testSuiteId);
}
