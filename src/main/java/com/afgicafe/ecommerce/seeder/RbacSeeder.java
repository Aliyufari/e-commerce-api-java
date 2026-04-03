package com.afgicafe.ecommerce.seeder;

import com.afgicafe.ecommerce.enums.Permission;
import com.afgicafe.ecommerce.enums.Role;
import com.afgicafe.ecommerce.repository.PermissionRepository;
import com.afgicafe.ecommerce.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class RbacSeeder implements CommandLineRunner {
    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;

    @Override
    public void run(String... args) throws Exception {
        for (Permission permission : Permission.values()){
            permissionRepository.findByName(permission.getValue())
                    .orElseGet(() -> {
                        com.afgicafe.ecommerce.entity.Permission p = new com.afgicafe.ecommerce.entity.Permission();
                        p.setName(permission.getValue());
                        return permissionRepository.save(p);
                    });
        }

        for (Role role : Role.values()){
            com.afgicafe.ecommerce.entity.Role rol = roleRepository.findByName(role.name())
                    .orElseGet(() -> {
                        com.afgicafe.ecommerce.entity.Role r = new com.afgicafe.ecommerce.entity.Role();
                        r.setName(role.name());
                        return roleRepository.save(r);
                    });

            List<com.afgicafe.ecommerce.entity.Permission> permissions = role.getPermissions()
                    .stream()
                    .map(p -> permissionRepository.findByName(p.getValue())
                    .orElseThrow()).collect(Collectors.toList());

            rol.setPermissions(permissions);
            roleRepository.save(rol);
        }
    }
}
