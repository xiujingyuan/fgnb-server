package com.fgnb.controller;

import com.fgnb.domain.Project;
import com.fgnb.enums.ProjectType;
import com.fgnb.service.ProjectService;
import com.fgnb.vo.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by jiangyitao.
 */
@RestController
@RequestMapping("/project")
@Slf4j
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    /**
     * 获取项目类型
     */
    @GetMapping("/types")
    public Response getProjectTypes(){
        List<Map> projectTypes = new ArrayList();

        for(ProjectType projectType: ProjectType.values()){
            Map projectTypeMap = new HashMap();
            projectTypeMap.put("projectTypeId",projectType.getType());
            projectTypeMap.put("projectTypeName",projectType.getName());
            projectTypes.add(projectTypeMap);
        }

        return Response.success(projectTypes);

    }

    /**
     * 项目名
     * @return
     */
    @GetMapping("/names")
    public Response getProjectNames(){
        return Response.success(projectService.getProjectNames());
    }

    /**
     * 新增项目
     * @return
     */
    @PostMapping("/add")
    public Response add(@Validated Project project){
        projectService.add(project);
        return Response.success("新增成功");
    }

    /**
     * 查询项目列表
     * @return
     */
    @GetMapping("/list")
    public Response queryList(Project project){
        return Response.success(projectService.queryList(project));
    }

    /**
     * 删除项目
     * @param projectId
     * @return
     */
    @GetMapping("/delete")
    public Response delete(Integer projectId){
        if(projectId == null){
            return Response.fail("项目id不能为空");
        }
        projectService.delete(projectId);
        return Response.success("删除成功");
    }

    /**
     * 修改项目
     */
    @PostMapping("/update")
    public Response update(@Validated Project project){
        projectService.update(project);
        return Response.success("修改成功");
    }

//    /**
//     * 根据projectType获取projects
//     * @param projectType
//     * @return
//     */
//    @GetMapping("/getProjectsByProjectType")
//    public Response getProjectsByProjectType(Integer projectType){
//        if(projectType == null){
//            return Response.fail("项目类型不能为空");
//        }
//        return Response.success(projectService.getProjectsByProjectType(projectType));
//    }


}
