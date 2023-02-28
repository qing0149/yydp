package com.llkj.hosp.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.llkj.hosp.repository.ScheduleRepository;
import com.llkj.hosp.service.ScheduleService;
import com.llkj.yygh.model.hosp.Schedule;
import com.llkj.yygh.vo.hosp.ScheduleQueryVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;

@Service
public class ScheduleServiceImpl implements ScheduleService {

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Override
    public void save(Map<String, Object> paramMap) {
        Schedule schedule = BeanUtil.mapToBean(paramMap, Schedule.class, true, new CopyOptions());
        //根据医院编号 和 排班编号查询
        Schedule scheduleExist = scheduleRepository.
                getScheduleByHoscodeAndHosScheduleId(schedule.getHoscode(), schedule.getHosScheduleId());
        //判断
        if (scheduleExist != null) {
            scheduleExist.setUpdateTime(new Date());
            scheduleExist.setIsDeleted(0);
            scheduleExist.setStatus(1);
            scheduleRepository.save(scheduleExist);
        } else {
            schedule.setCreateTime(new Date());
            schedule.setUpdateTime(new Date());
            schedule.setIsDeleted(0);
            schedule.setStatus(1);
            scheduleRepository.save(schedule);
        }
    }

    @Override
    public Page<Schedule> selectPage(int page, int limit, ScheduleQueryVo scheduleQueryVo) {
        Sort sort = Sort.by(Sort.Direction.DESC, "createTime");
        PageRequest pageable = PageRequest.of((page - 1), limit, sort);
        Schedule schedule = new Schedule();
        BeanUtil.copyProperties(scheduleQueryVo, schedule);
        ExampleMatcher matcher = ExampleMatcher.matching().withIgnoreCase(true);
        Example<Schedule> example = Example.of(schedule);
        Page<Schedule> all = scheduleRepository.findAll(example, pageable);
        return all;
    }
    @Override
    public void remove(String hoscode, String hosScheduleId) {
        Schedule schedule = scheduleRepository.getScheduleByHoscodeAndHosScheduleId(hoscode, hosScheduleId);
        if(null != schedule) {
            scheduleRepository.deleteById(schedule.getId());
        }
    }
}