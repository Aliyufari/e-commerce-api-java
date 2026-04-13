package com.afgicafe.ecommerce.seeder;

import com.afgicafe.ecommerce.entity.Permission;
import com.afgicafe.ecommerce.entity.Role;
import com.afgicafe.ecommerce.enums.PermissionEnum;
import com.afgicafe.ecommerce.enums.RoleEnum;
import com.afgicafe.ecommerce.repository.PermissionRepository;
import com.afgicafe.ecommerce.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Profile("!test")
public class RbacSeeder implements CommandLineRunner {
    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;

    @Override
    public void run(String... args) throws Exception {
        for (PermissionEnum permissionEnum : PermissionEnum.values()){
            permissionRepository.findByName(permissionEnum.getValue())
                    .orElseGet(() -> {
                        Permission p = new Permission();
                        p.setName(permissionEnum.getValue());
                        return permissionRepository.save(p);
                    });
        }

        for (RoleEnum roleEnum : RoleEnum.values()){
            Role rol = roleRepository.findByName(roleEnum.name())
                    .orElseGet(() -> {
                        Role r = new Role();
                        r.setName(roleEnum.name());
                        return roleRepository.save(r);
                    });

            List<Permission> permissions = roleEnum.getPermissionEnums()
                    .stream()
                    .map(p -> permissionRepository.findByName(p.getValue())
                    .orElseThrow()).collect(Collectors.toList());

            rol.setPermissions(permissions);
            roleRepository.save(rol);
        }
    }
}
