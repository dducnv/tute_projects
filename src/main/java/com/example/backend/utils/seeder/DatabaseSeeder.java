package com.example.backend.utils.seeder;

import com.example.backend.entity.Role;
import com.example.backend.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DatabaseSeeder {
    @Autowired
    RoleRepository roleRepository;

    @EventListener
    public void seed(ContextRefreshedEvent event) {
        seedRoleTable();
    }

    private void seedRoleTable() {
        Role roleU = roleRepository.findByRoleName("USER");
        Role roleA = roleRepository.findByRoleName("ADMIN");
        Role roleSA = roleRepository.findByRoleName("SUPER_ADMIN");
        Role role1 = new Role();
        Role role2 = new Role();
        Role role3 = new Role();
        role1.setRoleName("USER");
        role2.setRoleName("ADMIN");
        role3.setRoleName("SUPER_ADMIN");

        List<Role> roles = new ArrayList<>();
        if (roleU.getRoleName().isEmpty()) {
            roles.add(role1);
        }
        if (roleA.getRoleName().isEmpty()) {
            roles.add(role2);
        }
        if (roleSA.getRoleName().isEmpty()) {
            roles.add(role3);
        }
        roleRepository.saveAll(roles);
    }
}
