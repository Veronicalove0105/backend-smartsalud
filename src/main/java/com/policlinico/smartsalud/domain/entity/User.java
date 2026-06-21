package com.policlinico.smartsalud.domain.entity;

public class User {
    private Long id;
    private String username;
    private String email;
    private String password;
    private String role; // Ej: "ADMIN", "MEDICO", "RECEPCIONISTA"
    private boolean active;

    // Constructor vacío (necesario para frameworks)
    public User() {
    }

    // Constructor completo
    public User(Long id, String username, String email, String password, String role, boolean active) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = role;
        this.active = active;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}