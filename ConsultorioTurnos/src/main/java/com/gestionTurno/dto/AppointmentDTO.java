package com.gestionTurno.dto;

import com.gestionTurno.model.AppointmentStatus;
import com.gestionTurno.model.Specialist;
import com.gestionTurno.model.UserApp;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentDTO {

    @NotNull(message = "The date cannot be null.")
    @FutureOrPresent(message = "The date must be today or in the future")
    private LocalDate date;

    @NotNull(message = "The time cannot be null.")
    private LocalTime time;

    @NotNull(message = "The State cannot be null.")
    private AppointmentStatus appointmentStatus = AppointmentStatus.SCHEDULED;

    @NotNull(message = "The user cannot be null.")
    private UserApp user;

    @NotNull(message = "The specialist cannot be null.")
    private Specialist specialist;

}
