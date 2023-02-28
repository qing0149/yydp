package com.llkj.hosp.repository;

import com.llkj.yygh.model.hosp.Department;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Map;

/**
 * @ClassName DepartMentRepository
 * @Description TODO
 * @Author qing
 * @Date 2023/2/28 11:38
 * @Version 1.0
 */
public interface DepartmentRepository extends MongoRepository<Department, String> {

    Department getByHoscodeAndDepcode(String hoscode, String depcode);
}
