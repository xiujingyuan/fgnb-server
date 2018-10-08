package com.fgnb.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.fgnb.domain.Project;
import com.fgnb.exception.BusinessException;
import com.fgnb.mapper.ProjectMapper;
import com.fgnb.vo.PageVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by jiangyitao.
 */
@Service
public class ProjectService extends BaseService{

    @Autowired
    private ProjectMapper projectMapper;

    /**
     * 新增项目
     * @param project
     * @return
     */
    public void add(Project project) {
        //同一个项目类型 不允许存在相同的项目名
        Project dbProject = projectMapper.findByProjectNameAndProjectType(project.getProjectName(), project.getProjectType());
        if(dbProject != null){
            throw new BusinessException("命名冲突");
        }

        project.setCreateTime(new Date());
        project.setCreatorUid(getUid());

        int row = projectMapper.addProject(project);
        if(row!=1){
            throw new BusinessException("新增失败");
        }
    }

    //获取所有项目名
    public List<String> getProjectNames(){
        return projectMapper.findProjectNames();
    }

    public PageVo queryList(Project project) {
        PageHelper.startPage(project.getPageIndex(),project.getCountPerPage(),"createTime desc");
        List<Project> projects = projectMapper.queryList(project);
        return PageVo.convert(new PageInfo(projects));
    }

    public void delete(Integer projectId) {
        int row = projectMapper.delete(projectId);
        if(row!=1){
            throw new BusinessException("删除失败");
        }
    }

    public void update(Project project) {

        Project dbProject = projectMapper.findByProjectNameAndProjectTypeAndIdIsNot(project);
        if(dbProject != null){
            throw new BusinessException("命名冲突");
        }
        project.setUpdateTime(new Date());
        project.setUpdatorUid(getUid());
        int row = projectMapper.updateProject(project);
        if(row!=1){
            throw new BusinessException("修改失败");
        }
    }

    public List<Project> getProjectsByProjectType(Integer projectType) {
        return projectMapper.getProjectsByProjectType(projectType);
    }
}
