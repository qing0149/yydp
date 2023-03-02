package com.llkj.hosp.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.llkj.hosp.repository.HospitalRepository;
import com.llkj.hosp.service.HospitalService;
import com.llkj.yydp.cmclient.DictFeignClient;
import com.llkj.yygh.common.handler.LLKJException;
import com.llkj.yygh.enums.DictEnum;
import com.llkj.yygh.model.hosp.BookingRule;
import com.llkj.yygh.model.hosp.Hospital;
import com.llkj.yygh.vo.hosp.HospitalQueryVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
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
    @Autowired
    private DictFeignClient dictFeignClient;

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
        if (hospital == null) {
            throw new LLKJException(20001, "医院信息有误");
        }
        return hospital;
    }

    @Override
    public Page<Hospital> selectPage(Long page, Long limit, HospitalQueryVo hospitalQueryVo) {
//        1.创建分页对象
//        1.1创建排序对象
        Sort sort = Sort.by(Sort.Direction.DESC, "createTime");
        PageRequest pageable = PageRequest.of((page.intValue() - 1), limit.intValue(), sort);
//        1.2创建分页对象
        Hospital hospital = BeanUtil.copyProperties(hospitalQueryVo, Hospital.class);
//        2.创建条件模板
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING)
                .withIgnoreCase(true);
        Example<Hospital> example = Example.of(hospital, matcher);
//        3.带条件带分页的擦查询
        Page<Hospital> pageModel = hospitalRepository.findAll(example, pageable);

        pageModel.getContent().forEach(item -> {
            this.packHospital(item);
        });
        return pageModel;
    }

    @Override
    public void updateStauts(String id, Integer status) {
        Hospital hospital = hospitalRepository.findById(id).get();
        hospital.setStatus(status);
        hospitalRepository.save(hospital);
        hospital.setUpdateTime(new Date());
    }

    @Override
    public Map<String, Object> show(String id) {
//        根据id查询，并翻译字段
        Hospital hospital = packHospital(hospitalRepository.findById(id).get());
//        从医院获取信息
        BookingRule bookingRule = hospital.getBookingRule();
        hospital.setBookingRule(null);
        //3封装数据返回
        Map<String, Object> map = new HashMap<>();
        map.put("hospital", hospital);
        map.put("bookingRule", bookingRule);
        return map;
    }

    private Hospital packHospital(Hospital hospital) {
        String hostypeString = dictFeignClient.getName(DictEnum.HOSTYPE.getDictCode(), hospital.getHostype());
        String provinceString = dictFeignClient.getName(hospital.getProvinceCode());
        String cityString = dictFeignClient.getName(hospital.getCityCode());
        String districtString = dictFeignClient.getName(hospital.getDistrictCode());
        hospital.getParam().put("hostypeString", hostypeString);
        hospital.getParam().put("fullAddress", provinceString + cityString + districtString + hospital.getAddress());
        return hospital;
    }
}
