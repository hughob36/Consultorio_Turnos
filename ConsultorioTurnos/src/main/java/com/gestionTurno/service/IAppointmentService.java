package com.gestionTurno.service;


import com.gestionTurno.dto.AppointmentDTO;
import com.gestionTurno.dto.AppointmentResponseDTO;
import com.gestionTurno.model.Appointment;
import com.gestionTurno.model.AppointmentStatus;

import java.util.List;
import java.util.Optional;

public interface IAppointmentService {

    public List<AppointmentResponseDTO> findAll();
    public Optional<AppointmentResponseDTO> findById(Long id);
    public AppointmentResponseDTO save(AppointmentDTO appointmentDTO);
    public AppointmentResponseDTO updateAppointmentById(Long id, AppointmentStatus status);
    public AppointmentDTO updateAppointment(Long id, AppointmentDTO appointmentDTO);
}
