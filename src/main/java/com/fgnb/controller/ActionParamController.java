package com.fgnb.controller;

import com.fgnb.service.ActionParamService;
import com.fgnb.vo.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by jiangyitao.
 */
@RestController
@RequestMapping("/actionParam")
public class ActionParamController {

    @Autowired
    private ActionParamService actionParamService;

    @GetMapping("/findByActionId")
    public Response findActionParamsByActionId(Integer actionId){
        if(actionId == null){
            return Response.fail("actionId不能为空");
        }
        return Response.success(actionParamService.findActionParamsByActionId(actionId));
    }
}
