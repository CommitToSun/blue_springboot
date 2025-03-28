package org.example.blue_springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "org.example")
public class BlueSpringbootApplication {

    public static void main(String[] args) {
        SpringApplication.run(BlueSpringbootApplication.class, args);
    }

}
