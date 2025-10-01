package com.gestionTurno.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@Table(name = "Appointments")
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate date;
    private LocalTime time;

    @Enumerated(EnumType.STRING)
    private AppointmentStatus appointmentStatus = AppointmentStatus.SCHEDULED;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    private UserApp user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "specialist_id", nullable = false)
    private Specialist specialist;
}
