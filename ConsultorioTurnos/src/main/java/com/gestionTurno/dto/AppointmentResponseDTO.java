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
public class AppointmentResponseDTO {

    private Long id;
    private LocalDate date;
    private LocalTime time;
    private AppointmentStatus appointmentStatus = AppointmentStatus.SCHEDULED;
    private UserApp user;
    private Specialist specialist;

}
