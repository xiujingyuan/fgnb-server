package com.fgnb.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.fgnb.domain.GlobalVar;
import com.fgnb.exception.BusinessException;
import com.fgnb.mapper.GlobalVarMapper;
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
public class GlobalVarService extends BaseService{

    @Autowired
    private GlobalVarMapper globalVarMapper;

    public void add(GlobalVar globalVar) {

        globalVar.setCreateTime(new Date());
        globalVar.setCreatorUid(getUid());

        try{
            int row = globalVarMapper.add(globalVar);
            if(row != 1){
                throw new BusinessException("添加全局变量失败");
            }
        }catch (DuplicateKeyException e){
            throw new BusinessException("命名冲突");
        }

    }

    public void update(GlobalVar globalVar) {

        globalVar.setUpdateTime(new Date());
        globalVar.setUpdatorUid(getUid());

        try{
            int row = globalVarMapper.update(globalVar);
            if(row != 1){
                throw new BusinessException("更新全局变量失败");
            }
        }catch (DuplicateKeyException e){
            throw new BusinessException("命名冲突");
        }

    }

    public PageVo queryList(GlobalVar globalVar) {
        PageHelper.startPage(globalVar.getPageIndex(),globalVar.getCountPerPage(),"createTime desc");
        List<GlobalVar> globalVars = globalVarMapper.queryList(globalVar);
        return PageVo.convert(new PageInfo(globalVars));
    }

    public void delete(Integer globalVarId) {
        int row = globalVarMapper.delete(globalVarId);
        if(row != 1){
            throw new BusinessException("删除失败");
        }
    }

    public List<GlobalVar> findByProjectId(Integer projectId) {
        return globalVarMapper.findGlobalVarsByProjectId(projectId);
    }
}
