package com.fgnb.mapper;

import com.fgnb.domain.Action;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * Created by jiangyitao.
 */
@Mapper
public interface ActionMapper {

    int addAction(Action action);

    /*1.同一个项目下自定义action(actionType=2)
      2.所有项目公用的基础action(projectId=null & actionType=1 & projectType=null )
      3.同一项目类型的基础action(projectId=null & actionType=1 & projectType)*/
    @Select("SELECT * FROM action " +
            "WHERE " +
            "(projectId = #{projectId} AND actionType = 2) " +
            "OR" +
            "(" +
            "projectId IS NULL " +
            "AND actionType = 1 " +
            "AND ( projectType IS NULL OR projectType = ( SELECT projectType FROM project WHERE projectId = #{projectId} ) ) " +
            ")" +
            "ORDER BY createTime DESC")
    List<Action> findSelectableActions(Integer projectId);

    List<Action> findActionsByPageId(Integer pageId);

    @Delete("delete from action where actionId = #{actionId}")
    int deleteAction(Integer actionId);

    int updateAction(Action action);

    Action getActionDetailInfoByActionId(Integer actionId);

    Action buildActionTree(Integer actionId);

    List<Action> dynamicQuery(Action action);

    @Select("select * from action where projectId = #{projectId} and actionType = #{actionType} order by createTime desc")
    List<Action> findActionByProjectIdAndActionType(@Param("projectId") Integer projectId, @Param("actionType") Integer actionType);

    List<Map> findCreatorByProjectIdAndActionType(@Param("projectId")Integer projectId,@Param("actionType") Integer actionType);

    List<Map> findUpdatorByProjectIdAndActionType(@Param("projectId")Integer projectId,@Param("actionType") Integer actionType);
}
