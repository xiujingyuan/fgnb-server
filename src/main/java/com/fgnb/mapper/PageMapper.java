package com.fgnb.mapper;

import com.fgnb.domain.Page;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by jiangyitao.
 */
@Mapper
public interface PageMapper {

    @Insert("insert into page " +
            "(pageName,projectId,categoryId,description,imgUrl,imgHeight,imgWidth,windowHierarchyJson,deviceId,creatorUid,createTime) " +
            "values" +
            "(#{pageName},#{projectId},#{categoryId},#{description},#{imgUrl},#{imgHeight},#{imgWidth},#{windowHierarchyJson},#{deviceId},#{creatorUid},#{createTime})")
    int addPage(Page page);

    @Select("select * from page where pageName=#{pageName} and projectId=#{projectId}")
    Page findPageByPageNameAndProjectId(Page page);

    @Select("select * from page where pageName=#{pageName} and projectId=#{projectId} and pageId <> #{pageId}")
    Page findPageByPageNameAndProjectIdAndIdIsNot(Page page);

    List<Page> findByPageCategory(Page page);

    @Delete("delete from page where pageId = #{pageId}")
    int delete(Integer pageId);

    @Select("select * from page where pageId = #{pageId}")
    Page findPageById(Integer pageId);


    int updatePage(Page page);
}
