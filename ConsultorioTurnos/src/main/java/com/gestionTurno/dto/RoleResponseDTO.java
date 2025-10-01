package com.gestionTurno.dto;

import com.gestionTurno.model.Permission;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class RoleResponseDTO {

    private Long id;
    private String role;
    private Set<Permission> permissionList = new HashSet<>();
}
