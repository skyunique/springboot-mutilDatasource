package com.learn.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * Master主数据源配置类
 */
@Configuration
@MapperScan(value = MasterDataSourceConfig.PACKAGE,sqlSessionFactoryRef = "masterSqlSessionFactory")
public class MasterDataSourceConfig {

    static final String PACKAGE = "com.learn.dao.master";
    static final String MAPPER_LOCATION = "classpath:mapper/master/*.xml";



}
