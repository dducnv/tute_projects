package com.example.backend.service;

import com.example.backend.entity.User;

import java.util.List;

public interface UserManageService {
    List<User> findAllUser();
}
