package com.policlinico.smartsalud.application.service;

import com.policlinico.smartsalud.application.dto.UserUseCase;
import com.policlinico.smartsalud.domain.entity.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class UserService implements UserUseCase {

    private final Map<Long, User> storage = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    @Override
    public User registerUser(User user) {
        Long id = idGenerator.getAndIncrement();
        user.setId(id);
        storage.put(id, user);
        return user;
    }

    @Override
    public User getUserById(Long id) {
        User user = storage.get(id);
        if (user == null) {
            throw new IllegalArgumentException("Usuario no encontrado con id: " + id);
        }
        return user;
    }

    @Override
    public List<User> getAllUsers() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public User updateUser(Long id, User updated) {
        User existing = getUserById(id);
        existing.setUsername(updated.getUsername());
        existing.setEmail(updated.getEmail());
        existing.setRole(updated.getRole());
        existing.setActive(updated.isActive());
        if (updated.getPassword() != null && !updated.getPassword().isBlank()) {
            existing.setPassword(updated.getPassword());
        }
        storage.put(id, existing);
        return existing;
    }

    @Override
    public void deleteUser(Long id) {
        if (!storage.containsKey(id)) {
            throw new IllegalArgumentException("Usuario no encontrado con id: " + id);
        }
        storage.remove(id);
    }
}