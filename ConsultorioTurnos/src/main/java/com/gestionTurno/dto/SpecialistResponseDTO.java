package com.gestionTurno.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class SpecialistResponseDTO {

    private Long id;
    private String name;
    private String lastname;
    private String specialty;
    private boolean active;
}
