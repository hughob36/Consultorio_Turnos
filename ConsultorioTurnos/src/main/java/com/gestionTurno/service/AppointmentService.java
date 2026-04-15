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
    public List<AppointmentResponseDTO> findById(Long id) {
        List<Appointment> appointment = appointmentRepository.findByUserId(id);
        return appointmentMapper.mapToResponseDTOList(appointment);
    }

    @Override
    public Optional<AppointmentResponseDTO> findByOneId(Long id) {
        Optional<Appointment> appointment = appointmentRepository.findById(id);
        if(appointment != null) {
            return appointmentMapper.mapToResponseDTO(appointment);
        }
        return null;
    }


    @Override
    public AppointmentResponseDTO save(AppointmentDTO appointmentDTO) {
        Appointment appointment = appointmentMapper.mapTo(appointmentDTO);

        Specialist specialist = specialistRepository.findById(appointment.getSpecialist().getId()).orElse(null);
        UserApp user = userAppRepository.findById(appointment.getUser().getId()).orElse(null);

        if(specialist == null || user == null) return null;

        boolean userHasConflict = appointmentRepository.existsByUserIdAndDateAndTimeAndAppointmentStatus(
                    user.getId(),
                    appointmentDTO.getDate(),
                    appointmentDTO.getTime(),
                    AppointmentStatus.SCHEDULED);

        boolean specialistHasConflict = appointmentRepository.existsBySpecialistIdAndDateAndTimeAndAppointmentStatus(
                specialist.getId(),
                appointmentDTO.getDate(),
                appointmentDTO.getTime(),
                AppointmentStatus.SCHEDULED);

        if (userHasConflict || specialistHasConflict) return null;

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

        Specialist specialist = specialistRepository.findById(appointmentDTO.getSpecialist().getId()).orElse(null);
        UserApp user = userAppRepository.findById(appointmentDTO.getUser().getId()).orElse(null);

        if(specialist == null || user == null) return null;

        boolean userHasConflict = appointmentRepository.existsByUserIdAndDateAndTimeAndAppointmentStatus(
                user.getId(),
                appointmentDTO.getDate(),
                appointmentDTO.getTime(),
                AppointmentStatus.CANCELED);

        boolean specialistHasConflict = appointmentRepository.existsBySpecialistIdAndDateAndTimeAndAppointmentStatus(
                specialist.getId(),
                appointmentDTO.getDate(),
                appointmentDTO.getTime(),
                AppointmentStatus.CANCELED);

        Appointment appointment = appointmentMapper.mapTo(appointmentDTO);

        if(appointmentFound != null && !userHasConflict && !specialistHasConflict) {
            appointmentFound.setDate(appointment.getDate());
            appointmentFound.setTime(appointment.getTime());
            appointmentFound.setAppointmentStatus(appointment.getAppointmentStatus());
            appointmentFound.setSpecialist(specialist);
            appointmentFound.setUser(user);
            return appointmentMapper.mapToDTO(appointmentRepository.save(appointmentFound));
        }
        return null;
    }
}
