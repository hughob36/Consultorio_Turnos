package com.gestionTurno.service;

import com.gestionTurno.dto.AppointmentDTO;
import com.gestionTurno.dto.AppointmentResponseDTO;
import com.gestionTurno.mapper.AppointmentMapper;
import com.gestionTurno.model.Appointment;
import com.gestionTurno.model.AppointmentStatus;
import com.gestionTurno.model.Specialist;
import com.gestionTurno.model.UserApp;
import com.gestionTurno.repository.IAppointmentRepository;
import com.gestionTurno.repository.ISpecialistRepository;
import com.gestionTurno.repository.IUserAppRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AppointmentService implements IAppointmentService {

    @Autowired
    private IAppointmentRepository appointmentRepository;

    @Autowired
    private AppointmentMapper appointmentMapper;

    @Autowired
    private ISpecialistRepository specialistRepository;

    @Autowired
    private IUserAppRepository userAppRepository;

    @Override
    public List<AppointmentResponseDTO> findAll() {
        List<Appointment> appointmentList = appointmentRepository.findAll();
        return appointmentMapper.mapToResponseDTOList(appointmentList);
    }

    @Override
    public Optional<AppointmentResponseDTO> findById(Long id) {
        Optional<Appointment> appointment = appointmentRepository.findById(id);
        return appointmentMapper.mapToResponseDTO(appointment);
    }

    @Override
    public AppointmentResponseDTO save(AppointmentDTO appointmentDTO) {
        Appointment appointment = appointmentMapper.mapTo(appointmentDTO);

        Specialist specialist = specialistRepository.findById(appointment.getSpecialist().getId()).orElse(null);
        UserApp user = userAppRepository.findById(appointment.getUser().getId()).orElse(null);

        appointment.setSpecialist(specialist);
        appointment.setUser(user);
        return appointmentMapper.mapToResponseDTO(appointmentRepository.save(appointment));
    }

    @Override
    public AppointmentResponseDTO updateAppointmentById(Long id, AppointmentStatus status) {

        Appointment appointment = appointmentRepository.findById(id).orElse(null);

        if(appointment != null) {
            appointment.setAppointmentStatus(status);
            return appointmentMapper.mapToResponseDTO(appointmentRepository.save(appointment));
        }
        return null;
    }

    @Override
    public AppointmentDTO updateAppointment(Long id, AppointmentDTO appointmentDTO) {

        Appointment appointmentFound = appointmentRepository.findById(id).orElse(null);
        Appointment appointment = appointmentMapper.mapTo(appointmentDTO);

        Specialist specialist = specialistRepository.findById(appointment.getSpecialist().getId()).orElse(null);
        UserApp user = userAppRepository.findById(appointment.getUser().getId()).orElse(null);

        if(appointmentFound != null) {
            appointmentFound.setDate(appointment.getDate());
            appointmentFound.setTime(appointment.getTime());
            appointmentFound.setAppointmentStatus(appointment.getAppointmentStatus());
            appointment.setSpecialist(specialist);
            appointment.setUser(user);
            return appointmentMapper.mapToDTO(appointmentRepository.save(appointmentFound));
        }
        return appointmentDTO;
    }
}
