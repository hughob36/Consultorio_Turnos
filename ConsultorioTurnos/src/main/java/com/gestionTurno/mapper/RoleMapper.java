package com.gestionTurno.mapper;

import com.gestionTurno.dto.RoleDTO;
import com.gestionTurno.dto.RoleResponseDTO;
import com.gestionTurno.model.Role;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class RoleMapper {

    public Role toEntity(RoleDTO roleDTO) {

        Role role = new Role();
        role.setRole(roleDTO.getRole());
        role.setPermissionList(roleDTO.getPermissionList());

        return role;
    }

    public List<RoleResponseDTO> roleResponseDTOList(List<Role> roleList) {

        List<RoleResponseDTO> listDTO = new ArrayList<>();

        for(Role role : roleList) {

            RoleResponseDTO roleResponseDTO = new RoleResponseDTO();

            roleResponseDTO.setId(role.getId());
            roleResponseDTO.setRole(role.getRole());
            roleResponseDTO.setPermissionList(role.getPermissionList());

            listDTO.add(roleResponseDTO);
        }
        return listDTO;
    }

    public Optional<RoleResponseDTO> toResponseDTO(Optional<Role> role) {

        return role.map(roledto -> {
            RoleResponseDTO roleResponseDTO = new RoleResponseDTO();
            roleResponseDTO.setId(roledto.getId());
            roleResponseDTO.setRole(roledto.getRole());
            roleResponseDTO.setPermissionList(roledto.getPermissionList());
            return roleResponseDTO;
        });
    }

    public RoleResponseDTO roleResponseDTO(Role role) {

        RoleResponseDTO roleResponseDTO = new RoleResponseDTO();
        roleResponseDTO.setId(role.getId());
        roleResponseDTO.setRole(role.getRole());
        roleResponseDTO.setPermissionList(role.getPermissionList());

        return roleResponseDTO;
    }

}
