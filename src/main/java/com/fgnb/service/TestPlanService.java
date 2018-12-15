package com.fgnb.service;

import com.fgnb.domain.TestPlan;
import com.fgnb.domain.TestPlanBefore;
import com.fgnb.domain.TestPlanTestSuite;
import com.fgnb.dto.TestPlanDTO;
import com.fgnb.enums.TestPlanBeforeType;
import com.fgnb.exception.BusinessException;
import com.fgnb.mapper.TestPlanBeforeMapper;
import com.fgnb.mapper.TestPlanMapper;
import com.fgnb.mapper.TestPlanTestSuiteMapper;
import com.fgnb.vo.testplan.TestPlanInfoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by jiangyitao.
 */
@Service
public class TestPlanService extends BaseService{
    @Autowired
    private TestPlanMapper testPlanMapper;
    @Autowired
    private TestPlanTestSuiteMapper testPlanTestSuiteMapper;
    @Autowired
    private TestPlanBeforeMapper testPlanBeforeMapper;

    @Transactional
    public void addTestPlan(TestPlan testPlan) {

        //TestPlan表
        testPlan.setCreateTime(new Date());
        testPlan.setCreatorUid(getUid());

        try{
            int row = testPlanMapper.addTestPlan(testPlan);
            if(row != 1){
                throw new BusinessException("保存testplan失败");
            }
        }catch (DuplicateKeyException e){
            throw new BusinessException("命名冲突");
        }

        //插入TestPlanTestSuite表 TestPlanBefore表
        handleBeforeAndSuiteData(testPlan);
    }

    /**
     * 插入TestPlanTestSuite表 TestPlanBefore表
     * @param testPlan
     */
    private void handleBeforeAndSuiteData(TestPlan testPlan) {
        Integer testPlanId = testPlan.getTestPlanId();
        //TestPlanTestSuite表
        List<Integer> testPlanTestSuiteIds = testPlan.getTestPlanTestSuiteIds();
        List<TestPlanTestSuite> testPlanTestSuites = new ArrayList<>();
        if(!CollectionUtils.isEmpty(testPlanTestSuiteIds)){
            for(Integer testSuiteId:testPlanTestSuiteIds){
                TestPlanTestSuite suite = new TestPlanTestSuite();
                suite.setTestPlanId(testPlanId);
                suite.setTestSuiteId(testSuiteId);
                testPlanTestSuites.add(suite);
            }
        }
        if(!CollectionUtils.isEmpty(testPlanTestSuites)){
            int testPlanTestSuiteRow = testPlanTestSuiteMapper.addTestSuites(testPlanTestSuites);
            if(testPlanTestSuiteRow != testPlanTestSuites.size()){
                throw new BusinessException("保存TestPlanTestSuit失败");
            }
        }

        //TestPlanBefore表
        List<TestPlanBefore> testPlanBefores = new ArrayList<>();
        //beforeMethod
        Integer beforeMethodActionId = testPlan.getTestPlanBeforeMethodActionId();
        if(beforeMethodActionId != null){
            TestPlanBefore beforeMethod = new TestPlanBefore();
            beforeMethod.setActionId(beforeMethodActionId);
            beforeMethod.setTestPlanId(testPlanId);
            beforeMethod.setType(TestPlanBeforeType.BEFORE_METHOD.getType());
            testPlanBefores.add(beforeMethod);
        }
        //beforeSuite
        Integer beforeSuiteActionId = testPlan.getTestPlanBeforeSuiteActionId();
        if(beforeSuiteActionId != null){
            TestPlanBefore beforSuite = new TestPlanBefore();
            beforSuite.setActionId(beforeSuiteActionId);
            beforSuite.setTestPlanId(testPlanId);
            beforSuite.setType(TestPlanBeforeType.BEFORE_SUITE.getType());
            testPlanBefores.add(beforSuite);
        }

        if(!CollectionUtils.isEmpty(testPlanBefores)){
            int beforeRow = testPlanBeforeMapper.addTestPlanBefores(testPlanBefores);
            if(beforeRow != testPlanBefores.size()){
                throw new BusinessException("保存TestPlanBefore失败");
            }
        }
    }

    public List<TestPlan> findTestPlansByProjectId(Integer projectId) {
        return testPlanMapper.findTestPlansByProjectId(projectId);
    }

    public void deleteTestPlan(Integer testPlanId) {
        int row = testPlanMapper.deleteTestPlan(testPlanId);
        if(row != 1){
            throw new BusinessException("删除失败");
        }
    }

    public TestPlanDTO findTestPlanDetailInfoByTestPlanId(Integer testPlanId) {
        TestPlanDTO testPlanDto = testPlanMapper.findTestPlanDetailInfoByTestPlanId(testPlanId);
        //处理数据
        if(testPlanDto != null){
            //1.testPlanBeforeList里的数据 赋值给TestPlanDto.testPlanBeforeSuiteActionName/id与TestPlanDto.testPlanBeforeMethodActionName/id
            List<TestPlanBefore> testPlanBeforeList = testPlanDto.getTestPlanBeforeList();
            if(testPlanBeforeList!=null){
                for(TestPlanBefore testPlanBefore:testPlanBeforeList){
                    if(testPlanBefore.getType() == TestPlanBeforeType.BEFORE_SUITE.getType()){
                        testPlanDto.setTestPlanBeforeSuiteActionId(testPlanBefore.getActionId());
                        testPlanDto.setTestPlanBeforeSuiteActionName(testPlanBefore.getActionName());
                    }else if(testPlanBefore.getType() == TestPlanBeforeType.BEFORE_METHOD.getType()){
                        testPlanDto.setTestPlanBeforeMethodActionId(testPlanBefore.getActionId());
                        testPlanDto.setTestPlanBeforeMethodActionName(testPlanBefore.getActionName());
                    }
                }
            }
            //2.testPlanTestSuites里的数据 赋值给TestPlanDto.testPlanTestSuiteIds
            List<TestPlanTestSuite> testPlanTestSuites = testPlanDto.getTestPlanTestSuites();
            if(!CollectionUtils.isEmpty(testPlanTestSuites)){
                List<Integer> testSuiteIds = new ArrayList<>();
                for(TestPlanTestSuite testPlanTestSuite:testPlanTestSuites){
                    testSuiteIds.add(testPlanTestSuite.getTestSuiteId());
                }
                testPlanDto.setTestPlanTestSuiteIds(testSuiteIds);
            }
        }
        return testPlanDto;
    }

    @Transactional
    public void updateTestPlan(@Valid TestPlan testPlan) {
        if(testPlan.getTestPlanId() == null){
            throw new BusinessException("测试计划不能为空");
        }

        //保存TestPlan表数据
        testPlan.setUpdateTime(new Date());
        testPlan.setUpdatorUid(getUid());

        try {
            int row = testPlanMapper.update(testPlan);
            if(row != 1){
                throw new BusinessException("更新TestPlan失败");
            }
        }catch (DuplicateKeyException e){
            throw new BusinessException("命名冲突");
        }

        //删除TestPlanBefore数据
        testPlanBeforeMapper.deleteByTestPlanId(testPlan.getTestPlanId());
        //删除TestPlanTestSuite数据
        testPlanTestSuiteMapper.deleteByTestPlanId(testPlan.getTestPlanId());

        //插入TestPlanTestSuite表 TestPlanBefore表
        handleBeforeAndSuiteData(testPlan);
    }

    public TestPlanInfoVo findTestPlanInfoVoByTestPlanId(Integer testPlanId) {
        TestPlanInfoVo testPlanInfoVo = testPlanMapper.findTestPlanInfoVoByTestPlanId(testPlanId);
        List<TestPlanBefore> testPlanBeforeList = testPlanInfoVo.getTestPlanBeforeList();
        if(testPlanBeforeList!=null){
            for(TestPlanBefore testPlanBefore:testPlanBeforeList){
                if(testPlanBefore.getType() == TestPlanBeforeType.BEFORE_SUITE.getType()){
                    testPlanInfoVo.setTestPlanBeforeSuiteActionName(testPlanBefore.getActionName());
                }else if(testPlanBefore.getType() == TestPlanBeforeType.BEFORE_METHOD.getType()){
                    testPlanInfoVo.setTestPlanBeforeMethodActionName(testPlanBefore.getActionName());
                }
            }
        }
        return testPlanInfoVo;
    }

    //根据测试计划id查出所有测试用例id
    public List<Integer> findTestCaseIdsByTestPlanId(Integer testPlanId) {
        return testPlanMapper.findTestCaseIdsByTestPlanId(testPlanId);
    }
}
