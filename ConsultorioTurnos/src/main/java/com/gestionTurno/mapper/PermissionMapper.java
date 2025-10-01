package com.gestionTurno.mapper;

import com.gestionTurno.dto.PermissionDTO;
import com.gestionTurno.dto.PermissionResponseDTO;
import com.gestionTurno.model.Permission;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class PermissionMapper {

    public Permission toEntity(PermissionDTO permissionDTO) {

        Permission permission = new Permission();
        permission.setPermissionName(permissionDTO.getPermissionName());

        return permission;
    }

    public List<PermissionResponseDTO> toResponseDTOList(List<Permission> permissionList) {

        List<PermissionResponseDTO> listDTO = new ArrayList<>();

        for(Permission permission : permissionList) {

            PermissionResponseDTO permissionResponseDTO = new PermissionResponseDTO();
            permissionResponseDTO.setId(permission.getId());
            permissionResponseDTO.setPermissionName(permission.getPermissionName());

            listDTO.add(permissionResponseDTO);
        }
        return listDTO;
    }

    public Optional<PermissionResponseDTO> toResponseDTO(Optional<Permission> permission) {

       return permission.map(permissiondto -> {
           PermissionResponseDTO permissionResponseDTO = new PermissionResponseDTO();
           permissionResponseDTO.setId(permissiondto.getId());
           permissionResponseDTO.setPermissionName(permissiondto.getPermissionName());

           return permissionResponseDTO;
       });

    }

    public PermissionResponseDTO permissionResponseDTO(Permission permission) {

        PermissionResponseDTO permissionResponseDTO = new PermissionResponseDTO();
        permissionResponseDTO.setId(permission.getId());
        permissionResponseDTO.setPermissionName(permission.getPermissionName());

        return permissionResponseDTO;
    }

}
