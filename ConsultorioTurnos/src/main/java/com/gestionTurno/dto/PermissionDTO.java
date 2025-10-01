package com.gestionTurno.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class PermissionDTO {

    @NotBlank(message = "Empty name.")
    @Size(min = 2, max = 30, message = "Long name must be between 2 and 50 characters.")
    private String permissionName;
}
