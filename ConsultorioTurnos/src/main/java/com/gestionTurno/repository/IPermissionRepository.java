package com.gestionTurno.repository;

import com.gestionTurno.model.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IPermissionRepository extends JpaRepository<Permission, Long> {

    Optional<Permission> findByPermissionName(String permissionName);
}
