package com.llkj.hosp.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.llkj.yygh.model.hosp.HospitalSet;
import org.springframework.stereotype.Service;

public interface HospitalSetService extends IService<HospitalSet> {
    String getSignKey(String hoscode);
    
}

