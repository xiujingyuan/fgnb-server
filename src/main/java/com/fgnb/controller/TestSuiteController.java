package com.fgnb.controller;

import com.fgnb.domain.TestSuite;
import com.fgnb.service.TestSuiteService;
import com.fgnb.vo.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * Created by jiangyitao.
 */
@RestController
@Slf4j
@RequestMapping("/testSuite")
public class TestSuiteController {

    @Autowired
    private TestSuiteService testSuiteService;

    @PostMapping("/add")
    public Response addTestSutie(@Validated @RequestBody TestSuite testSuite){
        testSuiteService.addTestSuite(testSuite);
        return Response.success("添加测试集成功");
    }

    @PostMapping("/update")
    public Response updateTestSuite(@Validated @RequestBody TestSuite testSuite){
        if(testSuite.getTestSuiteId() == null){
            return Response.fail("测试集id不能为空");
        }
        testSuiteService.updateTestSuite(testSuite);
        return Response.success("修改测试集成功");
    }

    @GetMapping("/findTestSuitesByProjectId")
    public Response findTestSuitesByProjectId(Integer projectId){
        if(projectId == null){
            return Response.fail("ProjectId不能为空");
        }
        return Response.success(testSuiteService.findTestSuitesByProjectId(projectId));
    }

    @GetMapping("/delete")
    public Response deleteTestSuite(Integer testSuiteId){
        if(testSuiteId == null){
            return Response.fail("testSuiteId不能为空");
        }
        testSuiteService.deleteTestSuite(testSuiteId);
        return Response.success("删除成功");
    }

    /**
     * 根据testSuiteId查询测试集的详细信息
     * @param testSuiteId
     * @return
     */
    @GetMapping("/getTestSuiteDetailInfoByTestSuiteId")
    public Response getTestSuiteDetailInfoByTestSuiteId(Integer testSuiteId){
        if(testSuiteId == null){
            return Response.fail("testSuiteId不能为空");
        }
        return Response.success(testSuiteService.getTestSuiteDetailInfoByTestSuiteId(testSuiteId));
    }
}
