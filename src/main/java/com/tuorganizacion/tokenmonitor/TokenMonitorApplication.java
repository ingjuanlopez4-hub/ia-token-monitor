package com.tuorganizacion.tokenmonitor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.tuorganizacion.tokenmonitor")
@EntityScan(basePackages = "com.tuorganizacion.tokenmonitor")
public class TokenMonitorApplication {

    public static void main(String[] args) {
        SpringApplication.run(TokenMonitorApplication.class, args);
    }
}
