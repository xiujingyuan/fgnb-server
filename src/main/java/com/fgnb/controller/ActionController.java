package com.fgnb.controller;

import com.fgnb.domain.Action;
import com.fgnb.dto.ActionDTO;
import com.fgnb.service.ActionService;
import com.fgnb.vo.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * Created by jiangyitao.
 */
@RestController
@RequestMapping("/action")
public class ActionController {
    @Autowired
    private ActionService actionService;

    /**
     * 动态查询action
     * @param action
     * @return
     */
    @PostMapping("/list")
    public Response list(@RequestBody Action action){
       return Response.success(actionService.dynamicQuery(action));
    }

    /**
     * 添加action
     * @param action
     * @return
     */
    @PostMapping("/add")
    public Response addAction(@RequestBody @Validated Action action){
        //测试用例 必须要有分类
        if(action.getActionType() == 3){
            if(action.getCategoryId() == null){
                return Response.fail("测试用例分类不能为空");
            }
        }
        actionService.addAction(action);
        return Response.success("添加成功");
    }

    /**
     * 找出可供选择的action(基础的action与pageAction)
     * @param projectId
     * @return
     */
    @GetMapping("/findSelectableActions")
    public Response findSelectableActions(Integer projectId){
        if(projectId == null){
            return Response.fail("projectId不能为空");
        }
        return Response.success(actionService.findSelectableActions(projectId));
    }

    /**
     * 通过pageId查出相关的action
     * @param pageId
     * @return
     */
    @GetMapping("/findActionsByPageId")
    public Response findActionsByPageId(Integer pageId,Integer pageIndex,Integer countPerPage){
        if(pageId == null || pageIndex == null || countPerPage == null){
            return Response.fail("参数不合法");
        }
        return Response.success(actionService.findActionsByPageId(pageId,pageIndex,countPerPage));
    }

    @GetMapping("/delete")
    public Response deleteAction(Integer actionId){
        if(actionId == null){
            return Response.fail("actionId不能为空");
        }
        actionService.deleteAction(actionId);
        return Response.success("删除成功");
    }

    @PostMapping("/update")
    public Response updateAction(@RequestBody @Validated Action action){
        actionService.updateAction(action);
        return Response.success("更新成功");
    }

    /**
     * 根据actionId获取action详细信息，用于修改action
     * @param actionId
     * @return
     */
    @GetMapping("/getActionDetailInfoByActionId")
    public Response getActionDetailInfoByActionId(Integer actionId){
        if(actionId == null){
            return Response.fail("actionId不能为空");
        }
        return Response.success(actionService.getActionDetailInfoByActionId(actionId));
    }

    @PostMapping("/debug")
    public Response debugAction(@RequestBody ActionDTO actionDTO){
        actionService.debug(actionDTO);
        return Response.success("执行成功");
    }

    /**
     * 通过项目id和actionType查询action
     * @param projectId
     * @param actionType
     * @return
     */
    @GetMapping("/findActionByProjectIdAndActionType")
    public Response findActionByProjectIdAndActionType(Integer projectId,Integer actionType){
        if(projectId == null || actionType == null){
            return Response.fail("projectId或actionType不能为空");
        }
        return Response.success(actionService.findActionByProjectIdAndActionType(projectId,actionType));
    }

    /**
     * 通过项目id和actionType查询action创建人
     * @param projectId
     * @param actionType
     * @return
     */
    @GetMapping("/findCreatorByProjectIdAndActionType")
    public Response findCreatorByProjectIdAndActionType(Integer projectId,Integer actionType){
        if(projectId == null || actionType == null){
            return Response.fail("projectId或actionType不能为空");
        }
        return Response.success(actionService.findCreatorByProjectIdAndActionType(projectId,actionType));
    }

    /**
     * 通过项目id和actionType查询action更新人
     * @param projectId
     * @param actionType
     * @return
     */
    @GetMapping("/findUpdatorByProjectIdAndActionType")
    public Response findUpdatorByProjectIdAndActionType(Integer projectId,Integer actionType){
        if(projectId == null || actionType == null){
            return Response.fail("projectId或actionType不能为空");
        }
        return Response.success(actionService.findUpdatorByProjectIdAndActionType(projectId,actionType));
    }
}
