package com.gestionTurno.service;


import com.gestionTurno.dto.PermissionDTO;
import com.gestionTurno.dto.PermissionResponseDTO;
import com.gestionTurno.model.Permission;

import java.util.List;
import java.util.Optional;

public interface IPermissionService {

    public List<PermissionResponseDTO> findAll();
    public Optional<PermissionResponseDTO> findByid(Long id);
    public Optional<Permission> findByIdPermission(Long id);
    public PermissionResponseDTO save(PermissionDTO permissionDTO);
    public boolean deleteById(Long id);
    public PermissionResponseDTO update(Long id, PermissionDTO permissionDTO);
}
