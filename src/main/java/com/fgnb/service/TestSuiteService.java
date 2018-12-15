package com.fgnb.service;

import com.fgnb.domain.TestSuite;
import com.fgnb.domain.TestSuiteTestCase;
import com.fgnb.exception.BusinessException;
import com.fgnb.mapper.TestSuiteMapper;
import com.fgnb.mapper.TestSuiteTestCaseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.DuplicateFormatFlagsException;
import java.util.List;

/**
 * Created by jiangyitao.
 */
@Service
public class TestSuiteService extends BaseService{

    @Autowired
    private TestSuiteMapper testSuiteMapper;
    @Autowired
    private TestSuiteTestCaseMapper testSuiteTestCaseMapper;

    @Transactional
    public void addTestSuite(TestSuite testSuite) {

        testSuite.setCreateTime(new Date());
        testSuite.setCreatorUid(getUid());

        //TestSuite表
        try {
            int row = testSuiteMapper.addTestSuite(testSuite);
            if(row != 1){
                throw new BusinessException("添加测试集失败");
            }
        }catch (DuplicateKeyException e){
            throw new BusinessException("命名冲突");
        }
        //TestSuiteTestCase表
        addTestSuiteTestCaseData(testSuite);
    }

    private void addTestSuiteTestCaseData(TestSuite testSuite){
        List<Integer> testCaseIds = testSuite.getTestSuiteTestCaseIds();
        if(!CollectionUtils.isEmpty(testCaseIds)){
            List<TestSuiteTestCase> testSuiteTestCases = new ArrayList<>();
            //为每个TestSuiteTestCase设置 testSuiteId
            testCaseIds.forEach(testCaseId -> {
                TestSuiteTestCase testSuiteTestCase = new TestSuiteTestCase();
                testSuiteTestCase.setTestSuiteId(testSuite.getTestSuiteId());
                testSuiteTestCase.setTestCaseId(testCaseId);
                testSuiteTestCases.add(testSuiteTestCase);
            });

            int testCaseRow = testSuiteTestCaseMapper.add(testSuiteTestCases);
            if(testCaseRow!=testSuiteTestCases.size()){
                throw new BusinessException("添加testSuiteTestCase失败");
            }
        }
    }

    public void deleteTestSuite(Integer testSuiteId) {
        int row = testSuiteMapper.deleteTestSuite(testSuiteId);
        if(row != 1){
            throw new BusinessException("删除失败，请稍后重试");
        }
    }

    public List<TestSuite> findTestSuitesByProjectId(Integer projectId) {
        return testSuiteMapper.findTestSuitesByProjectId(projectId);
    }

    public TestSuite getTestSuiteDetailInfoByTestSuiteId(Integer testSuiteId) {
        return testSuiteMapper.getTestSuiteDetailInfoByTestSuiteId(testSuiteId);
    }

    @Transactional
    public void updateTestSuite(TestSuite testSuite) {

        //更新TestSuite表
        testSuite.setUpdateTime(new Date());
        testSuite.setUpdatorUid(getUid());
        try {
            int row = testSuiteMapper.updateTestSuite(testSuite);
            if(row != 1){
                throw new BusinessException("更新testSuite失败");
            }
        }catch (DuplicateKeyException e){
            throw new BusinessException("命名冲突");
        }

        //删除相关的测试用例
        testSuiteTestCaseMapper.deleteByTestSuiteId(testSuite.getTestSuiteId());
        //添加TestSuiteTestCase表
        addTestSuiteTestCaseData(testSuite);
    }
}
