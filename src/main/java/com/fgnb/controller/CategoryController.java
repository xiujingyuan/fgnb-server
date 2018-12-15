package com.fgnb.controller;

import com.fgnb.domain.Category;
import com.fgnb.service.CategoryService;
import com.fgnb.vo.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * Created by jiangyitao.
 */
@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping("/add")
    public Response addCategory(@RequestBody @Validated Category category) {
        categoryService.addCategory(category);
        return Response.success("添加分类成功");
    }

    @GetMapping("/list")
    public Response queryCategoryList(Integer projectId,Integer categoryType){
        if(projectId == null || categoryType == null){
            return Response.fail("项目id或分类类型不能为空");
        }
        return Response.success(categoryService.queryCategoryList(projectId,categoryType));
    }


}
