package com.gestionTurno.mapper;

import com.gestionTurno.dto.UserDTO;
import com.gestionTurno.dto.UserResponseDTO;
import com.gestionTurno.model.Role;
import com.gestionTurno.model.UserApp;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class UserAppMapper {

    public UserApp toEntity(UserDTO userDTO) {

        UserApp userApp = new UserApp();
        userApp.setName(userDTO.getName());
        userApp.setLastname(userDTO.getLastname());
        userApp.setEmail(userDTO.getEmail());
        userApp.setUserName(userDTO.getUserName());
        userApp.setPassword(userDTO.getPassword());
        userApp.setEnable(userDTO.isEnable());
        userApp.setAccountNotExpired(userDTO.isAccountNotExpired());
        userApp.setAccountNotLocked(userDTO.isAccountNotLocked());
        userApp.setCredentialNotExpired(userDTO.isCredentialNotExpired());
        userApp.setRoleList(userDTO.getRoleList());

        return userApp;
    }

    public UserResponseDTO toEntity(UserApp userApp) {

        UserResponseDTO userResponseDTO = new UserResponseDTO();
        userResponseDTO.setId(userApp.getId());
        userResponseDTO.setName(userApp.getName());
        userResponseDTO.setLastname(userApp.getLastname());
        userResponseDTO.setEmail(userApp.getEmail());
        userResponseDTO.setUserName(userApp.getUserName());
        userResponseDTO.setEnable(userApp.isEnable());
        userResponseDTO.setAccountNotExpired(userApp.isAccountNotExpired());
        userResponseDTO.setAccountNotLocked(userApp.isAccountNotLocked());
        userResponseDTO.setCredentialNotExpired(userApp.isCredentialNotExpired());
        userResponseDTO.setRoleList(userApp.getRoleList());

        return userResponseDTO;
    }

    public List<UserResponseDTO> userResponseDTOList(List<UserApp> userAppList) {

        List<UserResponseDTO> userResponseDTOList = new ArrayList<>();

        for(UserApp userApp : userAppList) {

            userResponseDTOList.add(this.toEntity(userApp));
        }
        return userResponseDTOList;
    }

    public Optional<UserResponseDTO> toUserOptional(Optional<UserApp> userApp) {
        return userApp.map(user -> {
            return this.toEntity(user);
        });
    }
}
