package com.example.tute_backend.service;

import com.example.tute_backend.entity.User;

import java.util.List;

public interface UserManageService {
    List<User> findAllUser();
}
