package com.llkj.hosp.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.llkj.hosp.mapper.HospitalSetMapper;
import com.llkj.hosp.service.HospitalSetService;
import com.llkj.yygh.common.handler.LLKJException;
import com.llkj.yygh.model.hosp.HospitalSet;
import org.springframework.stereotype.Service;

@Service
public class HospitalSetServiceImpl extends ServiceImpl<HospitalSetMapper, HospitalSet> implements HospitalSetService {

    /*
     * 获取签名
     * */
    @Override
    public String getSignKey(String hoscode) {
        QueryWrapper<HospitalSet> wrapper = new QueryWrapper<>();
        wrapper.eq("hoscode", hoscode);
        HospitalSet hospitalSet = baseMapper.selectOne(wrapper);
        if (hospitalSet == null) {
            throw new LLKJException(200001, "获取签名失败");
        }
        return hospitalSet.getSignKey();
    }
}