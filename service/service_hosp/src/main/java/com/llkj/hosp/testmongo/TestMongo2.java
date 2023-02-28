package com.llkj.hosp.testmongo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName TestMongo2
 * @Description TODO
 * @Author qing
 * @Date 2023/2/27 15:05
 * @Version 1.0
 */
@RestController
@RequestMapping("/mongo2")
public class TestMongo2 {
    @Autowired
    private UserRepository userRepository;

    @GetMapping("create")
    public void create() {
        User user = new User();
        user.setAge(20);
        user.setName("test");
        user.setEmail("4932200@qq.com");
        User save = userRepository.save(user);
        System.out.println(user);

    }

}
