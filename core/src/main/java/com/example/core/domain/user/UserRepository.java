package com.example.core.domain.user;

import com.example.core.domain.user.entity.User;
import com.example.core.domain.user.entity.UserId;

import java.util.Optional;

public interface UserRepository {

    Optional<User> findById(UserId id);
    Optional<User> findByEmail(String email);
    void save(User user);
    void deleteAll();
    int count();
}
