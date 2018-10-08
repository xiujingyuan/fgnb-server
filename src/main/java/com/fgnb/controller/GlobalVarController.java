package com.fgnb.controller;

import com.fgnb.domain.GlobalVar;
import com.fgnb.service.GlobalVarService;
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
@RequestMapping("/globalVar")
public class GlobalVarController {

    @Autowired
    private GlobalVarService globalVarService;

    @PostMapping("/add")
    public Response add(@RequestBody @Validated GlobalVar globalVar){
        globalVarService.add(globalVar);
        return Response.success("添加成功");
    }

    @PostMapping("/update")
    public Response update(@RequestBody @Validated GlobalVar globalVar){
        globalVarService.update(globalVar);
        return Response.success("修改成功");
    }

    @PostMapping("/list")
    public Response queryList(@RequestBody GlobalVar globalVar){
        return Response.success(globalVarService.queryList(globalVar));
    }

    @GetMapping("/delete")
    public Response delete(Integer globalVarId){
        if(globalVarId == null){
            return Response.fail("globalVarId不能为空");
        }
        globalVarService.delete(globalVarId);
        return Response.success("删除成功");
    }

    /**
     * 根据projectId查出全局变量
     * @param projectId
     * @return
     */
    @GetMapping("/findGlobalVarsByProjectId")
    public Response findByProjectId(Integer projectId){
        if(projectId == null){
            return Response.fail("projectId不能为空");
        }
        return Response.success(globalVarService.findByProjectId(projectId));
    }
}
