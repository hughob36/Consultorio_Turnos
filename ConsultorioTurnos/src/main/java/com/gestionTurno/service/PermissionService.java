package com.gestionTurno.service;

import com.gestionTurno.dto.PermissionDTO;
import com.gestionTurno.dto.PermissionResponseDTO;
import com.gestionTurno.mapper.PermissionMapper;
import com.gestionTurno.model.Permission;
import com.gestionTurno.repository.IPermissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PermissionService implements IPermissionService{

    @Autowired
    private IPermissionRepository permissionRepository;

    @Autowired
    private PermissionMapper permissionMapper;

    @Override
    public List<PermissionResponseDTO> findAll() {
        return permissionMapper.toResponseDTOList(permissionRepository.findAll());
    }

    @Override
    public Optional<PermissionResponseDTO> findByid(Long id) {

        return permissionMapper.toResponseDTO(permissionRepository.findById(id));
    }

    @Override
    public Optional<Permission> findByIdPermission(Long id) {
        return permissionRepository.findById(id);
    }

    @Override
    public PermissionResponseDTO save(PermissionDTO permissionDTO) {
        Permission permission = permissionMapper.toEntity(permissionDTO);
        return permissionMapper.permissionResponseDTO(permissionRepository.save(permission));
    }

    @Override
    public boolean deleteById(Long id) {

        Optional<Permission> permission = permissionRepository.findById(id);

        if(permission.isPresent()) {
            permissionRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public PermissionResponseDTO update(Long id, PermissionDTO permissionDTO) {

        Permission permissionFound = permissionRepository.findById(id).orElse(null);
        Permission permission = permissionMapper.toEntity(permissionDTO);

        if(permissionFound != null) {
            permissionFound.setPermissionName(permission.getPermissionName() != null?
                    permission.getPermissionName() : permissionFound.getPermissionName());
            return permissionMapper.permissionResponseDTO(permissionRepository.save(permissionFound));
        }
        return null;
    }
}
