package com.orange.user.service;


import com.orange.user.dto.UserDto;
import com.orange.user.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<UserDto> findAll();

    Optional<UserDto> findById(int id);

    int create(User newUser);

    Optional<User> update(User newUser, int id);

    void deleteById(int id);
}