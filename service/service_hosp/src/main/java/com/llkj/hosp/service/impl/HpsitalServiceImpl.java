package com.llkj.hosp.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.llkj.hosp.repository.HospitalRepository;
import com.llkj.hosp.service.HospitalService;
import com.llkj.yygh.common.handler.LLKJException;
import com.llkj.yygh.model.hosp.Hospital;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;

/**
 * @ClassName HpsitalServiceImpl
 * @Description TODO
 * @Author qing
 * @Date 2023/2/28 9:34
 * @Version 1.0
 */
@Service
public class HpsitalServiceImpl implements HospitalService {
    @Autowired
    private HospitalRepository hospitalRepository;

    @Override
    public void saveHospital(Map<String, Object> paramMap) {
//        1.参数转型map转换成hospitap
        String jsonString = JSONObject.toJSONString(paramMap);
        Hospital hospital = JSONObject.parseObject(jsonString, Hospital.class);
//        cn.hutool.json.JSONObject
//        2.根据hoscode查询医院信息
        Hospital targetHospital = hospitalRepository.getByHoscode(hospital.getHoscode());
        if (targetHospital != null) {
//            查询到医院信息，更新数据
//        3.查询到医院信息更新数据
            hospital.setId(targetHospital.getId());
            hospital.setCreateTime(targetHospital.getCreateTime());
            hospital.setUpdateTime(new Date());
            hospital.setIsDeleted(targetHospital.getIsDeleted());
            hospital.setStatus(targetHospital.getStatus());
            hospitalRepository.save(hospital);
        } else {
            //        4.查不到医院信息再做新增
            hospital.setCreateTime(new Date());
            hospital.setUpdateTime(new Date());
            hospital.setIsDeleted(0);
            hospital.setStatus(0);
            hospitalRepository.save(hospital);
        }
    }

    @Override
    public Hospital getHosByHoscode(String hoscode) {
        Hospital hospital = hospitalRepository.getByHoscode(hoscode);
        if (hospital==null){
            throw new LLKJException(20001,"医院信息有误");
        }
        return hospital;
    }
}
