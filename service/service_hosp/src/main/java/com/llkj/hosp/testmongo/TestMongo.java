package com.llkj.hosp.testmongo;

import com.mongodb.client.result.DeleteResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.regex.Pattern;

/**
 * @ClassName TestMongo
 * @Description TODO
 * @Author qing
 * @Date 2023/2/27 14:05
 * @Version 1.0
 */
@RestController
@RequestMapping("/mongo1")
public class TestMongo {
    @Autowired
    private MongoTemplate mongoTemplate;

    @GetMapping("create")
    public void create() {
        User user = new User();
        user.setAge(20);
        user.setName("test");
        user.setEmail("4932200@qq.com");
        User user1 = mongoTemplate.insert(user);
        System.out.println(user1);
    }

    @GetMapping("fingAll")
    public void findAll() {
        List<User> all = mongoTemplate.findAll(User.class);
        System.out.println(all);
    }

    @GetMapping("findId")
    public void getById() {
        User byId = mongoTemplate.findById("63fc4996ab43480ad2c89948", User.class);
        System.out.println(byId);
    }

    //条件查询
    @GetMapping("findUser")
    public void findUserList() {
        Query query = new Query(Criteria
                .where("name").is("test"));
//                .and("age").is(20));
        List<User> userList = mongoTemplate.find(query, User.class);
        System.out.println(userList);
    }

    //模糊查询
    @GetMapping("findLike")
    public void findUsersLikeName() {
        String name = "est";
        String regex = String.format("%s%s%s", "^.*", name, ".*$");
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Query query = new Query(Criteria.where("name").regex(pattern));
        List<User> userList = mongoTemplate.find(query, User.class);
        System.out.println(userList);
    }

    //删除操作
    @GetMapping("delete")
    public void delete() {
        Query query = new Query(Criteria.where("_id").is("63fc54073e197c36ba26924a"));
//                        .and("_id").is("63fc54073e197c36ba26924a"));
        DeleteResult result = mongoTemplate.remove(query, User.class);
        long count = result.getDeletedCount();
        System.out.println(count);
    }

}
