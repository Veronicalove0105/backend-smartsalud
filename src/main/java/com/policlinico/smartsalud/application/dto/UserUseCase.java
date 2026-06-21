package com.policlinico.smartsalud.application.dto;

import java.util.List;

import com.policlinico.smartsalud.domain.entity.User;

public interface UserUseCase {
    User registerUser(User user);

    User getUserById(Long id);

    List<User> getAllUsers();

    User updateUser(Long id, User user);

    void deleteUser(Long id);
}