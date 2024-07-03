package com.quizserver.repositories;

import com.quizserver.models.auth.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUserRepository extends JpaRepository<User, Integer> {
    User findByLogin(String login);
}