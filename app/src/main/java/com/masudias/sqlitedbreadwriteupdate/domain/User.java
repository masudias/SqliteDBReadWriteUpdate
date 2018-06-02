package com.masudias.sqlitedbreadwriteupdate.domain;

public class User {
    public String name;
    public Long createdAt;

    public User(String name, Long createdAt) {
        this.name = name;
        this.createdAt = createdAt;
    }
}