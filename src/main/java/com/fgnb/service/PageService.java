package com.fgnb.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.fgnb.domain.Page;
import com.fgnb.exception.BusinessException;
import com.fgnb.mapper.PageMapper;
import com.fgnb.vo.PageVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by jiangyitao.
 */
@Service
public class PageService extends BaseService{

    @Autowired
    private PageMapper pageMapper;

    public void add(Page page) {

        page.setCreateTime(new Date());
        page.setCreatorUid(getUid());

        try{
            int row = pageMapper.addPage(page);
            if(row!=1){
                throw new BusinessException("新增失败，请稍后重试");
            }
        }catch (DuplicateKeyException e){
            throw new BusinessException("命名冲突");
        }

    }

    public PageVo findByPageCategory(Page page) {
        PageHelper.startPage(page.getPageIndex(),page.getCountPerPage(),"createTime desc");
        List<Page> pages = pageMapper.findByPageCategory(page);
        return PageVo.convert(new PageInfo(pages));
    }

    public void delete(Integer pageId) {
        int row = pageMapper.delete(pageId);
        if(row != 1){
            throw new BusinessException("删除失败");
        }
    }

    public Page findPageById(Integer pageId) {
        return pageMapper.findPageById(pageId);
    }

    public void update(Page page) {

        page.setUpdateTime(new Date());
        page.setUpdatorUid(getUid());

        try{
            int row = pageMapper.updatePage(page);
            if(row != 1){
                throw new BusinessException("更新失败，请稍后重试");
            }
        }catch (DuplicateKeyException e){
            throw new BusinessException("命名冲突");
        }
    }
}
