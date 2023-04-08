package com.example.backend.service.impl;

import com.example.backend.dto.RoleDto;
import com.example.backend.entity.Role;
import com.example.backend.repository.RoleRepository;
import com.example.backend.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    @Autowired
    RoleRepository roleRepository;

    @Override
    public List<RoleDto> findAll() {
        return roleRepository.findAll().stream().map(this::toDto).collect(Collectors.toList());
    }

    @Override
    public boolean save() {
        return false;
    }

    @Override
    public boolean saveAll() {
        return false;
    }

    @Override
    public boolean delete() {
        return false;
    }

//    @Override
//    public boolean setRolesForUser(List<RoleDto> roleDtos) {
//        return false;
//    }

    @Override
    public RoleDto toDto(Role role) {
        return RoleDto.builder()
                .name(role.getRoleName())
                .description(role.getDescription())
                .build();
    }

}
