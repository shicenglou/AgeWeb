package com.age.music;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@MapperScan(basePackages = "com.age.music.mapper")
@ComponentScan(basePackages = "com.age")
public class MusicApplication {
    public static void main(String[] args) {
        SpringApplication.run(MusicApplication.class);
    }
}
