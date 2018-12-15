package com.fgnb.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.fgnb.domain.Project;
import com.fgnb.exception.BusinessException;
import com.fgnb.mapper.ProjectMapper;
import com.fgnb.vo.PageVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
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

        project.setCreateTime(new Date());
        project.setCreatorUid(getUid());

        try{
            int row = projectMapper.addProject(project);
            if(row!=1){
                throw new BusinessException("添加失败，请稍后重试");
            }
        }catch (DuplicateKeyException e){
            throw new BusinessException("命名冲突");
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

        project.setUpdateTime(new Date());
        project.setUpdatorUid(getUid());

        try {
            int row = projectMapper.updateProject(project);
            if(row!=1){
                throw new BusinessException("修改失败");
            }
        }catch (DuplicateKeyException e){
            throw new BusinessException("命名冲突");
        }

    }

}
