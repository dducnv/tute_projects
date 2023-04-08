package com.example.backend.service;

import com.example.backend.dto.RoleDto;
import com.example.backend.entity.Role;
import java.util.List;


public interface RoleService {
    List<RoleDto> findAll();
    boolean save();
    boolean saveAll();
    boolean delete();

//    boolean setRolesForUser(List<RoleDto> roleDtos);

    RoleDto toDto(Role role);
}
