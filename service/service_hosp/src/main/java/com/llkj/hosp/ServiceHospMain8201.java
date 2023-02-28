package com.llkj.hosp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import java.util.Iterator;

/**
 * @ClassName ServiceHospMain8201
 * @Description TODO
 * @Author qing
 * @Date 2023/2/20 10:29
 * @Version 1.0
 */
@SpringBootApplication
@EnableDiscoveryClient
public class ServiceHospMain8201 {
    public static void main(String[] args) {
//        adminApiConfig
        ConfigurableApplicationContext context = SpringApplication.run(ServiceHospMain8201.class);
//        Iterator<String> iterator = context.getBeanFactory().getBeanNamesIterator();
//        while (iterator.hasNext()){
//            System.out.println(iterator.next());
//        }
    }
}
