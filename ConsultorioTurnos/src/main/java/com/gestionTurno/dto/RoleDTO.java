package com.gestionTurno.dto;

import com.gestionTurno.model.Permission;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class RoleDTO {

    @NotBlank(message = "Empty role name.")
    @Size(min = 2, max = 30, message = "Long name must be between 2 and 50 characters.")
    private String role;

    @NotEmpty(message = "Empty list.")
    private Set<Permission> permissionList = new HashSet<>();
}
