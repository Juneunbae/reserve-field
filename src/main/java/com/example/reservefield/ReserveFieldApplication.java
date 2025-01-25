package com.example.reservefield;

import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.TimeZone;

@SpringBootApplication
public class ReserveFieldApplication {

    public static void main(String[] args) {
        SpringApplication.run(ReserveFieldApplication.class, args);
    }

    // Timezone 설정
    @PostConstruct
    public void init() {
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul"));
    }
}