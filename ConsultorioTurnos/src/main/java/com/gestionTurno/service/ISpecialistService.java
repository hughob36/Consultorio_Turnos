package com.gestionTurno.service;


import com.gestionTurno.dto.SpecialistDTO;
import com.gestionTurno.dto.SpecialistResponseDTO;
import com.gestionTurno.model.Specialist;

import java.util.List;
import java.util.Optional;

public interface ISpecialistService {

    public List<SpecialistResponseDTO> findAll();
    public Optional<SpecialistResponseDTO> findById(Long id);
    public SpecialistResponseDTO save(SpecialistDTO specialistDTO);
    public Optional<SpecialistResponseDTO> softDeleteById(Long id);
    public SpecialistDTO updateByID(Long id, SpecialistDTO specialistDTO);
}
