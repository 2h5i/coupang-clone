package com.taei.coupangclone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class CoupangCloneApplication {

    public static void main(String[] args) {
        SpringApplication.run(CoupangCloneApplication.class, args);
    }

}
