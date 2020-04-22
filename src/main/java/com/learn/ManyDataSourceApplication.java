package com.learn;

import com.github.pagehelper.autoconfigure.PageHelperAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//排除默认配置的分页插件
@SpringBootApplication(exclude = PageHelperAutoConfiguration.class)
public class ManyDataSourceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ManyDataSourceApplication.class,args);
    }
}
