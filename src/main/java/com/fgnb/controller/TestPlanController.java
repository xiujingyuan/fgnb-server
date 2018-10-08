package com.fgnb.controller;

import com.fgnb.domain.TestPlan;
import com.fgnb.service.TestPlanService;
import com.fgnb.vo.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Created by jiangyitao.
 */
@RestController
@RequestMapping("/testPlan")
public class TestPlanController {

    @Autowired
    private TestPlanService testPlanService;

    @PostMapping("/add")
    public Response addTestPlan(@RequestBody @Validated TestPlan testPlan){
        testPlanService.addTestPlan(testPlan);
        return Response.success("添加成功");
    }

    @PostMapping("/update")
    public Response updateTestPlan(@RequestBody @Valid TestPlan testPlan){
        testPlanService.updateTestPlan(testPlan);
        return Response.success("更新成功");
    }


    /**
     * 根据项目id查出测试计划
     * @param projectId
     * @return
     */
    @GetMapping("/findTestPlansByProjectId")
    public Response findTestPlansByProjectId(Integer projectId){
        if(projectId == null){
            return Response.fail("projectId不能为空");
        }
        return Response.success(testPlanService.findTestPlansByProjectId(projectId));
    }

    @GetMapping("/delete")
    public Response deleteTestPlan(Integer testPlanId){
        if(testPlanId == null){
            return Response.fail("testPlanId不能为空");
        }
        testPlanService.deleteTestPlan(testPlanId);
        return Response.success("删除成功");
    }

    @GetMapping("/findTestPlanDetailInfoByTestPlanId")
    public Response findTestPlanDetailInfo(Integer testPlanId){
        if(testPlanId == null){
            return Response.fail("testPlanId不能为空");
        }
        return Response.success(testPlanService.findTestPlanDetailInfoByTestPlanId(testPlanId));
    }

    /**
     * 根据测试计划查询计划信息 （前端：测试计划 -> 点击提交测试）
     * @return
     */
    @GetMapping("/findTestPlanInfoVoByTestPlanId")
    public Response findTestPlanInfoVoByTestPlanId(Integer testPlanId){
        if(testPlanId == null){
            return Response.fail("testPlanId不能为空");
        }
        return Response.success(testPlanService.findTestPlanInfoVoByTestPlanId(testPlanId));
    }

}
