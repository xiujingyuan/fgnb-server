package com.fgnb.service;

import com.fgnb.domain.Category;
import com.fgnb.exception.BusinessException;
import com.fgnb.mapper.CategoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
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
        //CategoryName和projectId和categoryType 相同的情况 不允许重名
        Category dbCategory = categoryMapper.findByCategoryNameProjectIdAndCategoryType(category.getCategoryName(), category.getProjectId(), category.getCategoryType());
        if(dbCategory!=null){
            throw new BusinessException("命名冲突");
        }

        category.setCreateTime(new Date());
        int row = categoryMapper.addCategory(category);
        if(row!=1){
            throw new BusinessException("添加分类失败");
        }
    }

    public List<Category> queryCategoryList(Integer projectId, Integer categoryType) {
        return categoryMapper.queryCategoryList(projectId,categoryType);
    }

    @Transactional
    public void deleteCategory(Integer categoryId) {
        int row = categoryMapper.deleteCategory(categoryId);
        if(row != 1){
            throw new BusinessException("删除失败");
        }
        //todo 删除分类下的item
    }

    public void updateCategory(Category category) {
        int row = categoryMapper.updateCategory(category);
        if(row != 1){
            throw new BusinessException("更新分类失败");
        }
    }
}
