package com.example.backend.utils.seeder;

import com.example.backend.entity.Role;
import com.example.backend.repository.RoleRepository;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class DatabaseSeeder {
    @Autowired
    RoleRepository roleRepository;

    @EventListener
    public void seed(ContextRefreshedEvent event) {
        seedRoleTable();
    }

    private void seedRoleTable() {
        Optional<Role> roleU = Optional.ofNullable(roleRepository.findByRoleName("USER"));
        Optional<Role> roleA = Optional.ofNullable(roleRepository.findByRoleName("ADMIN"));

        if(!roleU.isPresent()){
            Role role1 = new Role();
            role1.setRoleName("USER");
        }
        if(!roleA.isPresent()){
            Role role2 = new Role();
            role2.setRoleName("ADMIN");
            roleRepository.save(role2);
        }
    }
}
