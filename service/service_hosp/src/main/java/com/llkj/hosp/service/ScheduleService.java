package com.llkj.hosp.service;

import com.llkj.yygh.model.hosp.Schedule;
import com.llkj.yygh.vo.hosp.ScheduleQueryVo;
import org.springframework.data.domain.Page;

import java.util.Map;

public interface ScheduleService {
    void save(Map<String, Object> paramMap);

    Page<Schedule> selectPage(int page, int limit, ScheduleQueryVo scheduleQueryVo);
    void remove(String hoscode, String hosScheduleId);
}
