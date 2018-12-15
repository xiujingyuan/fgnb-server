package com.fgnb.mapper;

import com.fgnb.domain.GlobalVar;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * Created by jiangyitao.
 */
@Mapper
public interface GlobalVarMapper {
    @Insert("insert into global_var " +
            "(globalVarName,globalVarValue,description,projectId,creatorUid,createTime) " +
            "values " +
            "(#{globalVarName},#{globalVarValue},#{description},#{projectId},#{creatorUid},#{createTime})")
    int add(GlobalVar globalVar);

    @Update("update global_var set " +
            "globalVarName = #{globalVarName}," +
            "globalVarValue = #{globalVarValue},"+
            "description = #{description},"+
            "updateTime = #{updateTime},"+
            "updatorUid = #{updatorUid} "+
            "where globalVarId = #{globalVarId}")
    int update(GlobalVar globalVar);

    List<GlobalVar> queryList(GlobalVar globalVar);

    @Delete("delete from global_var where globalVarId = #{globalVarId}")
    int delete(Integer globalVarId);

    @Select("select * from global_var where projectId = #{projectId}")
    List<GlobalVar> findGlobalVarsByProjectId(Integer projectId);
}
