package com.fgnb.controller;

import com.fgnb.service.ActionParamPossibleValueService;
import com.fgnb.vo.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by jiangyitao.
 */
@RestController
@RequestMapping("/actionParamPossibleValue")
public class ActionParamPossibleValueController {

    @Autowired
    private ActionParamPossibleValueService actionParamPossibleValueService;

    @GetMapping("/findByActionParamId")
    public Response findActionParamPossibleValuesByActionParamId(Integer actionParamId){
        if(actionParamId == null){
            return Response.fail("actionParamId不能为空");
        }
        return Response.success(actionParamPossibleValueService.findActionParamPossibleValuesByActionParamId(actionParamId));
    }
}
