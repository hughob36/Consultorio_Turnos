package com.gestionTurno.dto;

import com.gestionTurno.model.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    @NotBlank(message = "Name cannot was empty.")
    private String name;
    @NotBlank(message = "Lastname cannot was empty.")
    private String lastname;

    @Email(message = "The email must be valid.")
    @NotBlank(message = "The email must not be empty.")
    private String email;
    @NotBlank(message = "The username must not be empty.")
    private String userName;

    @NotBlank(message = "The password must not be empty.")
    @Size(min = 6, message = "The password must be at least 6 characters long.")
    private String password;
    @NotNull(message = "The enable state cannot be null.")
    private boolean enable;
    @NotNull(message = "The accountNotExpired state cannot be null.")
    private boolean accountNotExpired;
    @NotNull(message = "The accountNotLocked state cannot be null.")
    private boolean accountNotLocked;
    @NotNull(message = "The credentialNotExpired state cannot be nul.")
    private boolean credentialNotExpired;
    @NotNull(message = "You must assign at least one role.")
    private Set<Role> roleList = new HashSet<>();
}
