package com.gestionTurno.service;

import com.gestionTurno.dto.RoleDTO;
import com.gestionTurno.dto.RoleResponseDTO;
import com.gestionTurno.mapper.RoleMapper;
import com.gestionTurno.model.Role;
import com.gestionTurno.repository.IRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoleService implements IRoleService{

    @Autowired
    private IRoleRepository roleRepository;

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public List<RoleResponseDTO> findAll() {
        return roleMapper.roleResponseDTOList(roleRepository.findAll());
    }

    @Override
    public Optional<RoleResponseDTO> findById(Long id){
        return roleMapper.toResponseDTO(roleRepository.findById(id));
    }


    @Override
    public Optional<Role> findByIdRole(Long id) {
        return roleRepository.findById(id);
    }

    @Override
    public RoleResponseDTO save(RoleDTO roleDTO) {
        Role role = roleMapper.toEntity(roleDTO);
        return roleMapper.roleResponseDTO(roleRepository.save(role));
    }

    @Override
    public boolean deleteById(Long id) {

        if(roleRepository.existsById(id)){
            roleRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public RoleResponseDTO update(Long id, RoleDTO roleDTO) {

        Role roleFound = roleRepository.findById(id).orElse(null);
        Role role = roleMapper.toEntity(roleDTO);

        if(roleFound != null) {
            roleFound.setRole(role.getRole() != null? role.getRole() : roleFound.getRole());
            roleFound.setPermissionList(role.getPermissionList());
            return roleMapper.roleResponseDTO(roleRepository.save(roleFound));
        }
        return roleMapper.roleResponseDTO(roleFound);
    }
}
