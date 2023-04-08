package com.example.backend.service;

import com.example.backend.dto.*;
import com.example.backend.entity.User;

public interface UserService {
    UserInfoDto myInfo();
    UserInfoDto findByUsername(String username);
    UserInfoDto toUserDTO(User user);
    User findUserByEmail(String email);
}
