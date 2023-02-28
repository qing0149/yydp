package com.llkj.yydp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.llkj"})
@EnableDiscoveryClient
public class ServiceCmn8202 {

    public static void main(String[] args) {
        SpringApplication.run(ServiceCmn8202.class, args);
    }
}