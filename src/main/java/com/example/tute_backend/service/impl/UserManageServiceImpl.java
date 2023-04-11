package com.example.tute_backend.service.impl;

import com.example.tute_backend.entity.User;
import com.example.tute_backend.repository.UserRepository;
import com.example.tute_backend.service.UserManageService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
@Service
@Transactional
@RequiredArgsConstructor
public class UserManageServiceImpl implements UserManageService {
    @Autowired
    UserRepository userRepository;
    @Override
    public List<User> findAllUser() {
        return userRepository.findAll();
    }
}
