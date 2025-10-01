package com.gestionTurno.dto;

import com.gestionTurno.model.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseDTO {

    private Long id;
    private String name;
    private String lastname;
    private String email;
    private String userName;
    private boolean enable;
    private boolean accountNotExpired;
    private boolean accountNotLocked;
    private boolean credentialNotExpired;
    private Set<Role> roleList = new HashSet<>();

}
