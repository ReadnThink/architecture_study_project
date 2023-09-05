package com.example.sns_project.domain.user;

import com.example.sns_project.domain.user.entity.User;
import com.example.sns_project.domain.user.entity.UserId;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository {

    Optional<User> findById(UserId id);
    Optional<User> findByEmail(String email);
    void save(User user);
    void deleteAll();
    int count();
}