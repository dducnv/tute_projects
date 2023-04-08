package com.example.tute_backend.service;

import com.example.tute_backend.dto.*;
import com.example.tute_backend.entity.User;

public interface UserService {
    UserInfoDto myInfo();
    UserInfoDto findByUsername(String username);
    UserInfoDto toUserDTO(User user);
    User findUserByEmail(String email);
}
