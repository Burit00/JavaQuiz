package com.quizserver.seeders;

import com.quizserver.enums.UserRole;
import com.quizserver.models.entities.User;
import com.quizserver.repositories.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Seeder {
    @Autowired
    private IUserRepository userRepository;

    @Bean
    public CommandLineRunner commandLineRunner() {
        return args -> {
            if (userRepository.findByLogin("admin") == null) {
                User user = new User("admin", "admin", UserRole.ADMIN);
                userRepository.save(user);
            }
        };
    }
}
