package com.example.tute_backend.service.impl;

import com.example.tute_backend.dto.*;
import com.example.tute_backend.entity.User;
import com.example.tute_backend.repository.RoleRepository;
import com.example.tute_backend.repository.UserRepository;
import com.example.tute_backend.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service(value = "userService")
@Slf4j
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserDetailsService, UserService {
    private final int expireTime = 60 * 1000 * 5;
    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Override
    public UserInfoDto myInfo(){
        User user = getUserFromToken();
        System.out.println(user.getName());
       return toUserDTO(user);
    }


    @Override
    public UserInfoDto findByUsername(String username) {
        User user = userRepository.findUserByUsername(username);
        return toUserDTO(user);
    }

    @Override
    public User findUserByEmail(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        return user.orElse(null);
    }
    public User findUserByUsernameOrEmail(String email, String username) {
        User user = userRepository.findFirstByEmailOrUsername(email,username);
        return user;
    }


    public User getUserFromToken(){
        Object userInfo = SecurityContextHolder.getContext().getAuthentication().getName();
        System.out.println(userInfo);
        return findUserByEmail(userInfo.toString());
    };
    @Override
    public UserInfoDto toUserDTO(User user){
        return UserInfoDto.builder()
                .avatar(user.getAvatar())
                .name(user.getName())
                .username(user.getUsername())
                .username(user.getUsername())
                .email(user.getEmail())
                .createdAt(user.getCreatedAt())
                .build();
    }
    private Set<SimpleGrantedAuthority> getAuthority(User user) {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        System.out.println(user.getRoles());
        user.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getRoleName()));
        });
        return authorities;
    }
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findUserByEmail(username);
        if(user == null){
            throw new UsernameNotFoundException("Invalid username or password.");
        }
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), getAuthority(user));
    }
}
