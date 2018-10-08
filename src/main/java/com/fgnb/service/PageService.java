package com.fgnb.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.fgnb.domain.Page;
import com.fgnb.exception.BusinessException;
import com.fgnb.mapper.PageMapper;
import com.fgnb.vo.PageVo;
import org.springframework.beans.factory.annotation.Autowired;
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
        //同一个项目不能有相同的page名字
        Page dbPage = pageMapper.findPageByPageNameAndProjectId(page);
        if(dbPage != null){
            throw new BusinessException("命名冲突");
        }

        page.setCreateTime(new Date());
        page.setCreatorUid(getUid());

        int row = pageMapper.addPage(page);
        if(row!=1){
            throw new BusinessException("新增失败，请稍后重试");
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

        //修改后的page名 不能和同一个项目下其他page相同
        Page dbPage = pageMapper.findPageByPageNameAndProjectIdAndIdIsNot(page);
        if(dbPage != null){
            throw new BusinessException("命名冲突");
        }

        page.setUpdateTime(new Date());
        page.setUpdatorUid(getUid());

        int row = pageMapper.updatePage(page);
        if(row != 1){
            throw new BusinessException("更新失败，请稍后重试");
        }
    }
}
