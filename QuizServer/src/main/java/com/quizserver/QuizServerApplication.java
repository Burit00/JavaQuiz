package com.quizserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@PropertySource("file:${user.dir}/.env")
@SpringBootApplication
public class QuizServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(QuizServerApplication.class, args);
    }
}
