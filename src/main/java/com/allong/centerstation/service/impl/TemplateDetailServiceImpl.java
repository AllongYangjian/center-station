package com.allong.centerstation.service.impl;

import com.allong.centerstation.domain.entity.TemplateDetail;
import com.allong.centerstation.mapper.TemplateDetailMapper;
import com.allong.centerstation.service.TemplateDetailService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 模板详情 服务实现类
 * </p>
 *
 * @author 杨建
 * @since 2020-12-09
 */
@Service
public class TemplateDetailServiceImpl extends ServiceImpl<TemplateDetailMapper, TemplateDetail> implements TemplateDetailService {

    @Override
    public List<TemplateDetail> listByTempId(Integer tempId) {
        return baseMapper.selectList(new QueryWrapper<TemplateDetail>().eq("temp_id",tempId));
    }

    @Override
    public void deleteByTempId(Integer tempId) {
        baseMapper.delete(new QueryWrapper<TemplateDetail>().eq("temp_id",tempId));
    }

    @Override
    public List<TemplateDetail> listByTempIdAndType(Integer id, int type) {
        return baseMapper.selectList(new QueryWrapper<TemplateDetail>().eq("temp_id",id).eq("type",type));
    }

    @Override
    public boolean updateTemplateDetailBeforeSave(List<TemplateDetail> detailList) {
        //先获取旧的模板数据，然后将数据更新到新模板上，然后删除旧的数据
        if (detailList != null && detailList.size() > 0) {
            //缓存旧数据信息
            List<TemplateDetail> mOldData = baseMapper.selectList(new QueryWrapper<TemplateDetail>().eq("temp_id",detailList.get(0).getTempId()));
            if(mOldData!=null && mOldData.size()>0){
                for(TemplateDetail newT:detailList){
                    for(TemplateDetail oldT:mOldData){
                        if(newT.getKeyId().equals(oldT.getKeyId()) && newT.getType().equals(oldT.getType())){
                            newT.setKeySize(oldT.getKeySize());
                            newT.setScale(oldT.getScale());
                            break;
//                            newT.setFrameSize(oldT.getFrameSize()); //目前还是统一设置采样率好
                        }
                    }
                }
            }
            //删除旧数据
            deleteByTempId(detailList.get(0).getTempId());
           return saveBatch(detailList);
        }
        return false;
    }
}
