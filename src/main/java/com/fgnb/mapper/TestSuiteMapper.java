package com.fgnb.mapper;

import com.fgnb.domain.TestSuite;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by jiangyitao.
 */
@Mapper
public interface TestSuiteMapper {

    int addTestSuite(TestSuite testSuite);

    @Delete("delete from test_suite where testSuiteId = #{testSuiteId}")
    int deleteTestSuite(Integer testSuiteId);

    List<TestSuite> findTestSuitesByProjectId(Integer projectId);

    TestSuite getTestSuiteDetailInfoByTestSuiteId(Integer testSuiteId);


    int updateTestSuite(TestSuite testSuite);
}
