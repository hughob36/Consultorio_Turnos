package com.gestionTurno.service;

import com.gestionTurno.dto.SpecialistDTO;
import com.gestionTurno.dto.SpecialistResponseDTO;
import com.gestionTurno.mapper.SpecialistMapper;
import com.gestionTurno.model.Specialist;
import com.gestionTurno.repository.ISpecialistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SpecialistService implements ISpecialistService {

    @Autowired
    private ISpecialistRepository specialistRepository;

    @Autowired
    private SpecialistMapper specialistMapper;

    @Override
    public List<SpecialistResponseDTO> findAll() {
        return specialistMapper.toMapResponseDTOList(specialistRepository.findAll());
    }

    @Override
    public Optional<SpecialistResponseDTO> findById(Long id) {
        Optional<Specialist> specialist = specialistRepository.findById(id);
        return specialist.map(specialistResponse -> specialistMapper.toMapResponseDTO(specialistResponse));
    }

    @Override
    public SpecialistResponseDTO save(SpecialistDTO specialistDTO) {
        Specialist newSpecialist = specialistRepository.save(specialistMapper.toMap(specialistDTO));
        return specialistMapper.toMapResponseDTO(newSpecialist);
    }

    @Override
    public Optional<SpecialistResponseDTO> softDeleteById(Long id) {

        Optional<Specialist> specialistFound = specialistRepository.findById(id)
                .map(specialist -> {
                    specialist.setActive(false);
                    return specialistRepository.save(specialist);
                });
        return specialistFound.map(specialist -> specialistMapper.toMapResponseDTO(specialist));
    }

    @Override
    public SpecialistDTO updateByID(Long id, SpecialistDTO specialistDTO) {

        Specialist specialist = specialistMapper.toMap(specialistDTO);
        Specialist specialistFound = specialistRepository.findById(id).orElse(null);

        if(specialistFound != null) {
           specialistFound.setName(specialist.getName());
           specialistFound.setLastname(specialist.getLastname());
           specialistFound.setSpecialty(specialist.getSpecialty());
           specialistFound.setActive(specialist.isActive());
           return specialistMapper.toMapDTO(specialistRepository.save(specialistFound));
        }
        return null;
    }
}
