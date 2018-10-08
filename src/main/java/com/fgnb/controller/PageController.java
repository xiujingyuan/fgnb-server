package com.fgnb.controller;

import com.fgnb.domain.Page;
import com.fgnb.service.PageService;
import com.fgnb.vo.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * Created by jiangyitao.
 */
@Slf4j
@RestController
@RequestMapping("/page")
public class PageController {

    @Autowired
    private PageService pageService;
    /**
     * 新增
     */
    @PostMapping("/add")
    public Response add(@Validated Page page){
        pageService.add(page);
        return Response.success("新增page成功");
    }

    @PostMapping("/update")
    public Response update(@Validated Page page){
        pageService.update(page);
        return Response.success("修改page成功");
    }


    /**
     * 查询page分类下的page
     * @return
     */
    @PostMapping("/findByPageCategory")
    public Response findByPageCategory(@RequestBody Page page){
        return Response.success(pageService.findByPageCategory(page));
    }

    /**
     * 删除page
     * @param pageId
     * @return
     */
    @GetMapping("/delete")
    public Response delete(Integer pageId){
        if(pageId == null){
            return Response.fail("pageId不能为空");
        }
        pageService.delete(pageId);
        return Response.success("删除成功");
    }

    @GetMapping("/findPageById")
    public Response findPageById(Integer pageId){
        if(pageId == null){
            return Response.fail("pageId不能为空");
        }
        return Response.success(pageService.findPageById(pageId));
    }

}
