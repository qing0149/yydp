package com.llkj.hosp.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.llkj.yygh.model.hosp.Hospital;
import com.llkj.yygh.vo.hosp.HospitalQueryVo;
import org.springframework.data.domain.Page;

import java.util.Map;

/**
 * @ClassName HospitalService
 * @Description TODO
 * @Author qing
 * @Date 2023/2/28 9:33
 * @Version 1.0
 */
public interface HospitalService{
    void saveHospital(Map<String, Object> paramMap);

    Hospital getHosByHoscode(String hoscode);

    Page<Hospital> selectPage(Long page, Long limit, HospitalQueryVo hospitalQueryVo);

    void updateStauts(String id, Integer status);

    Map<String, Object> show(String id);
}
