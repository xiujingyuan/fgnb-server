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

    //基础的action与pageAction
    @Select("select * from action where (projectId = #{projectId} and actionType = 2) or projectId is null order by createTime desc")
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
