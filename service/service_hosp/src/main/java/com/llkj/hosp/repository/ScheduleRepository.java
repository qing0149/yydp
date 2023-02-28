package com.llkj.hosp.repository;

import com.llkj.yygh.model.hosp.Schedule;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @ClassName ScheduleRepository
 * @Description TODO
 * @Author qing
 * @Date 2023/2/28 15:18
 * @Version 1.0
 */
public interface ScheduleRepository extends MongoRepository<Schedule,String> {
    Schedule getScheduleByHoscodeAndHosScheduleId(String hoscode, String hosScheduleId);
}
