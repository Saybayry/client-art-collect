package com.example.client.model;

public class User {
    private int id;
    private String email;
    private String token;

    private String name;

    // Конструктор
    public User(int id, String email, String token, String name) {
        this.id = id;
        this.email = email;
        this.token = token;
        this.name = name;
    }
    public User() {
    }
    // Геттеры и сеттеры
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", token='" + token + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
