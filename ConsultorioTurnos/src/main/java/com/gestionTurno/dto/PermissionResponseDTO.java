package com.gestionTurno.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class PermissionResponseDTO {

    private Long id;
    private String permissionName;
}
