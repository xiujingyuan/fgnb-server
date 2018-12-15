package com.fgnb.mapper;

import com.fgnb.domain.Category;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * Created by jiangyitao.
 */
@Mapper
public interface CategoryMapper {
    @Insert("insert into category " +
            "(categoryName,categoryType,projectId,createTime) " +
            "values " +
            "(#{categoryName},#{categoryType},#{projectId},#{createTime})")
    int addCategory(Category category);

    @Select("select * from category where projectId = #{projectId} and categoryType = #{categoryType} order by createTime desc")
    List<Category> queryCategoryList(@Param("projectId") Integer projectId,@Param("categoryType") Integer categoryType);

}
