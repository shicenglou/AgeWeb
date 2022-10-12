package com.age.serviceindex;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.age")
@MapperScan(basePackages = "com.age.serviceindex.mapper")
public class ServiceIndexBaseApplicaation {
    public static void main(String[] args) {
        SpringApplication.run(ServiceIndexBaseApplicaation.class,args);

    }
}
