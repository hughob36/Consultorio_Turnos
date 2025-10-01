package com.gestionTurno.security.config;

import com.gestionTurno.model.Permission;
import com.gestionTurno.model.Role;
import com.gestionTurno.model.UserApp;
import com.gestionTurno.repository.IPermissionRepository;
import com.gestionTurno.repository.IRoleRepository;
import com.gestionTurno.repository.IUserAppRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Set;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initDatabase(IUserAppRepository userRepo,
                                   IRoleRepository roleRepo,
                                   PasswordEncoder passwordEncoder,
                                   IPermissionRepository permissionRepository) {
        return args -> {
            // Solo crear el admin si no hay usuarios
            if (userRepo.count() == 0) {

                Permission createPermission = permissionRepository
                        .findByPermissionName("CREATE")
                        .orElseGet(() -> permissionRepository.save(new Permission(null, "CREATE")));

                createPermission = permissionRepository.findById(createPermission.getId())
                        .orElseThrow(() -> new RuntimeException("Permission 'CREATE' not found."));

                Role adminRole = roleRepo.findByRole("ADMIN").orElse(null);
                if (adminRole == null) {
                    Set<Permission> permissionSet = new HashSet<>();
                    permissionSet.add(createPermission);
                    Role newRole = new Role();
                    newRole.setRole("ADMIN");
                    newRole.setPermissionList(permissionSet);
                    adminRole = roleRepo.save(newRole);
                }

                UserApp admin = new UserApp();
                admin.setName("Administrador");
                admin.setLastname("Del Sistema");
                admin.setEmail("admin@tudominio.com");
                admin.setUserName("admin");
                admin.setPassword(passwordEncoder.encode("admin123"));
                admin.setEnable(true);
                admin.setAccountNotExpired(true);
                admin.setAccountNotLocked(true);
                admin.setCredentialNotExpired(true);
                admin.getRoleList().add(adminRole);
                userRepo.save(admin);
            }
        };
    }
}
