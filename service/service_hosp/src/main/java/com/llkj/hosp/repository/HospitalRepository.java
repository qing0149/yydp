package com.llkj.hosp.repository;

import com.llkj.yygh.model.hosp.Hospital;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * @ClassName HospitalRepostory
 * @Description TODO
 * @Author qing
 * @Date 2023/2/28 9:32
 * @Version 1.0
 */
@Repository
public interface HospitalRepository extends MongoRepository<Hospital,String> {

    Hospital getByHoscode(String hoscode);
}
