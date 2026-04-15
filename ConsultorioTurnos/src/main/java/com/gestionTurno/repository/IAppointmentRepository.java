package com.gestionTurno.repository;

import com.gestionTurno.model.Appointment;
import com.gestionTurno.model.AppointmentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Repository
public interface IAppointmentRepository extends JpaRepository<Appointment, Long> {

    List<Appointment> findByUserId(Long userId);
    boolean existsByUserIdAndDateAndTimeAndAppointmentStatus(Long userId, LocalDate date, LocalTime time, AppointmentStatus status);
    boolean existsBySpecialistIdAndDateAndTimeAndAppointmentStatus(Long specialistId, LocalDate date, LocalTime time, AppointmentStatus status);
}
