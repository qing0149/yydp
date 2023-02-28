package com.llkj.yydp.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @ClassName CmnConfig
 * @Description TODO
 * @Author qing
 * @Date 2023/2/24 16:15
 * @Version 1.0
 */
@Configuration
@EnableTransactionManagement
@MapperScan("com.llkj.yydp.mapper")
public class CmnConfig {
}
