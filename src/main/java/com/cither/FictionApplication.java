package com.cither;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

/**
 * @author raincither
 */
@EnableCaching
@SpringBootApplication
public class FictionApplication {

    public static void main(String[] args) {
        SpringApplication.run(FictionApplication.class, args);
    }

}
