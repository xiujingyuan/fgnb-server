package com.fgnb.mapper;

import com.fgnb.domain.Project;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * Created by jiangyitao.
 */
@Mapper
public interface ProjectMapper {

    @Insert("insert into project" +
            "(projectName,description,projectType,createTime,creatorUid) " +
            "values" +
            "(#{projectName},#{description},#{projectType},#{createTime},#{creatorUid})")
    int addProject(Project project);

    @Select("select projectName from project group by projectName")
    List<String> findProjectNames();

    List<Project> queryList(Project project);

    @Delete("delete from project where projectId = #{projectId}")
    int delete(Integer projectId);

    int updateProject(Project project);

    @Select("select * from project where projectId = #{projectId}")
    Project findById(Integer projectId);

}
