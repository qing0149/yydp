package com.llkj.hosp.service;

import com.llkj.yygh.model.hosp.Hospital;

import java.util.Map;

/**
 * @ClassName HospitalService
 * @Description TODO
 * @Author qing
 * @Date 2023/2/28 9:33
 * @Version 1.0
 */
public interface HospitalService {
    void saveHospital(Map<String, Object> paramMap);

    Hospital getHosByHoscode(String hoscode);
}
