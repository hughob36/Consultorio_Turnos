package com.gestionTurno.dto;


import jakarta.validation.constraints.NotBlank;


public record AuthLoginRequestDTO(@NotBlank String userName,@NotBlank String password) {
}
