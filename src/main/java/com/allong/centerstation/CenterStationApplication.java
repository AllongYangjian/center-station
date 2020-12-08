package com.allong.centerstation;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.allong.centerstation.mapper")
public class CenterStationApplication {

    public static void main(String[] args) {
        SpringApplication.run(CenterStationApplication.class, args);
    }

}
