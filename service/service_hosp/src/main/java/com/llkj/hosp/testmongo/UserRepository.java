package com.llkj.hosp.testmongo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * @ClassName UserRepository
 * @Description TODO
 * @Author qing
 * @Date 2023/2/27 15:03
 * @Version 1.0
 */
@Repository
public interface UserRepository extends MongoRepository<User,String> {


}
