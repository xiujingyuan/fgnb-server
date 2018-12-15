package com.fgnb.service;

import com.fgnb.domain.Category;
import com.fgnb.exception.BusinessException;
import com.fgnb.mapper.CategoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Created by jiangyitao.
 */
@Service
public class CategoryService extends BaseService{

    @Autowired
    private CategoryMapper categoryMapper;

    public void addCategory(Category category) {

        category.setCreateTime(new Date());

        try {
            int row = categoryMapper.addCategory(category);
            if(row!=1){
                throw new BusinessException("添加分类失败");
            }
        }catch (DuplicateKeyException e){
            throw new BusinessException("命名冲突");
        }

    }

    public List<Category> queryCategoryList(Integer projectId, Integer categoryType) {
        return categoryMapper.queryCategoryList(projectId,categoryType);
    }

}
