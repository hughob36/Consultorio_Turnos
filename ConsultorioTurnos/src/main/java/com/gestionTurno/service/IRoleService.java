package com.gestionTurno.service;


import com.gestionTurno.dto.RoleDTO;
import com.gestionTurno.dto.RoleResponseDTO;
import com.gestionTurno.model.Role;

import java.util.List;
import java.util.Optional;

public interface IRoleService {

    public List<RoleResponseDTO> findAll();
    public Optional<RoleResponseDTO> findById(Long id);
    public Optional<Role> findByIdRole(Long id);
    public RoleResponseDTO save(RoleDTO roleDTO);
    public boolean deleteById(Long id);
    public RoleResponseDTO update(Long id, RoleDTO roleDTO);
}
