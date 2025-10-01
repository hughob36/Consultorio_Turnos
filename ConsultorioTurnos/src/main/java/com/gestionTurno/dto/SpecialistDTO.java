package com.gestionTurno.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class SpecialistDTO {

    @NotBlank(message = "Name cannot was empty.")
    @Size(max=50)
    private String name;
    @NotBlank(message = "Lastname cannot was empty.")
    @Size(max=50)
    private String lastname;
    @NotBlank(message = "The specialty cannot was empty.")
    private String specialty;
    @NotNull(message = "The state cannot be null.")
    private boolean active;
}
