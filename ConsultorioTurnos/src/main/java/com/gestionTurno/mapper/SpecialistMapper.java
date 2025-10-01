package com.gestionTurno.mapper;

import com.gestionTurno.dto.SpecialistDTO;
import com.gestionTurno.dto.SpecialistResponseDTO;
import com.gestionTurno.model.Specialist;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class SpecialistMapper {

    public Specialist toMap(SpecialistDTO specialistDTO) {

        Specialist specialist = new Specialist();
        specialist.setName(specialistDTO.getName());
        specialist.setLastname(specialistDTO.getLastname());
        specialist.setSpecialty(specialistDTO.getSpecialty());
        specialist.setActive(specialistDTO.isActive());
        return specialist;
    }

    public SpecialistDTO toMapDTO(Specialist specialist) {

        SpecialistDTO specialistDTO = new SpecialistDTO();
        specialistDTO.setName(specialist.getName());
        specialistDTO.setLastname(specialist.getLastname());
        specialistDTO.setSpecialty(specialist.getSpecialty());
        specialistDTO.setActive(specialist.isActive());
        return specialistDTO;
    }


    public SpecialistResponseDTO toMapResponseDTO(Specialist specialist) {

        SpecialistResponseDTO specialistResponseDTO = new SpecialistResponseDTO();
        specialistResponseDTO.setId(specialist.getId());
        specialistResponseDTO.setName(specialist.getName());
        specialistResponseDTO.setLastname(specialist.getLastname());
        specialistResponseDTO.setSpecialty(specialist.getSpecialty());
        specialistResponseDTO.setActive(specialist.isActive());
        return specialistResponseDTO;
    }

    public List<SpecialistResponseDTO> toMapResponseDTOList(List<Specialist> specialistList){

        List<SpecialistResponseDTO> specialistResponseDTOList = new ArrayList<>();
        for(Specialist specialist : specialistList) {
            specialistResponseDTOList.add(this.toMapResponseDTO(specialist));
        }
        return specialistResponseDTOList;
    }
}
