package com.gestionTurno.mapper;


import com.gestionTurno.dto.AppointmentDTO;
import com.gestionTurno.dto.AppointmentResponseDTO;
import com.gestionTurno.model.Appointment;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class AppointmentMapper {

    public Appointment mapTo(AppointmentDTO appointmentDTO) {

        Appointment appointment = new Appointment();
        appointment.setDate(appointmentDTO.getDate());
        appointment.setTime(appointmentDTO.getTime());
        appointment.setAppointmentStatus(appointmentDTO.getAppointmentStatus());
        appointment.setUser(appointmentDTO.getUser());
        appointment.setSpecialist(appointmentDTO.getSpecialist());
        return appointment;
    }

    public AppointmentDTO mapToDTO(Appointment appointment) {

        AppointmentDTO appointmentDTO = new AppointmentDTO();
        appointmentDTO.setDate(appointment.getDate());
        appointmentDTO.setTime(appointment.getTime());
        appointmentDTO.setAppointmentStatus(appointment.getAppointmentStatus());
        appointmentDTO.setUser(appointment.getUser());
        appointmentDTO.setSpecialist(appointment.getSpecialist());
        return appointmentDTO;
    }

    public AppointmentResponseDTO mapToResponseDTO(Appointment appointment) {

        AppointmentResponseDTO appointmentResponseDTO = new AppointmentResponseDTO();
        appointmentResponseDTO.setId(appointment.getId());
        appointmentResponseDTO.setDate(appointment.getDate());
        appointmentResponseDTO.setTime(appointment.getTime());
        appointmentResponseDTO.setAppointmentStatus(appointment.getAppointmentStatus());
        appointmentResponseDTO.setUser(appointment.getUser());
        appointmentResponseDTO.setSpecialist(appointment.getSpecialist());
        return appointmentResponseDTO;
    }

    public List<AppointmentResponseDTO> mapToResponseDTOList(List<Appointment> appointmentList) {

        List<AppointmentResponseDTO> appointmentResponseDTOList = new ArrayList<>();

        for(Appointment appointment : appointmentList) {
            appointmentResponseDTOList.add(this.mapToResponseDTO(appointment));
        }
        return appointmentResponseDTOList;
    }

    public Optional<AppointmentResponseDTO> mapToResponseDTO(Optional<Appointment> appointment) {
        return appointment.map(appointmentResponseDTO -> this.mapToResponseDTO(appointmentResponseDTO));
    }



}
