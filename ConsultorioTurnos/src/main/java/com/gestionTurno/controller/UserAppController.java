package com.gestionTurno.controller;

import com.gestionTurno.dto.ErrorResponseDTO;
import com.gestionTurno.dto.UserDTO;
import com.gestionTurno.dto.UserResponseDTO;
import com.gestionTurno.mapper.UserAppMapper;
import com.gestionTurno.model.Role;
import com.gestionTurno.model.UserApp;
import com.gestionTurno.service.IRoleService;
import com.gestionTurno.service.IUserAppService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/api/user")
@PreAuthorize("denyAll")
@Tag(name = "Usuarios", description = "Operaciones relacionadas con la gestión de usuarios.")
public class UserAppController {

    @Autowired
    private IUserAppService userAppService;

    @Autowired
    private IRoleService roleService;

    @Autowired
    private UserAppMapper userAppMapper;

    @Operation(summary = "Listar usuarios", description = "Obtiene la lista completa de usuarios registrados.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de usuarios obtenida correctamente.")
    })
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserResponseDTO>> getUsers() {

        List<UserResponseDTO> listResponseDTO = userAppService.finAll();
        return ResponseEntity.ok(listResponseDTO);
    }

    @Operation(summary = "Buscar usuario por ID", description = "Devuelve un usuario según su identificador único.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuario encontrado."),
            @ApiResponse(responseCode = "404", description = "No se encontró un usuario con el ID indicado.")
    })
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserResponseDTO> getUser(@PathVariable Long id) {

        Optional<UserResponseDTO> userResponseDTO = userAppService.findById(id);

        return userResponseDTO.map(ResponseEntity::ok)
                    .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Crear un usuario", description = "Registra un nuevo usuario con roles asignados.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Usuario creado exitosamente."),
            @ApiResponse(responseCode = "409", description = "El usuario ya existe.")
    })
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> createUser(@RequestBody @Valid UserDTO userDTO) {

        UserApp userApp = userAppMapper.toEntity(userDTO);

        userApp.setPassword(userAppService.encriptPassword(userApp.getPassword()));

        Set<Role> roleList = new HashSet<>();
        for(Role role : userApp.getRoleList()) {
            roleService.findByIdRole(role.getId()).ifPresent(roleList::add);
        }

        try {
            userApp.setRoleList(roleList);
            UserResponseDTO userResponseDTO = userAppService.save(userApp);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(userResponseDTO);

        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new ErrorResponseDTO("User '" + userDTO.getUserName() + "' already exists."));
        }
    }

    @Operation(summary = "Eliminar usuario", description = "Elimina un usuario existente según su ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Usuario eliminado correctamente."),
            @ApiResponse(responseCode = "404", description = "No se encontró un usuario con el ID indicado.")
    })
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteUserById(@PathVariable Long id) {

        if(userAppService.deleteById(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND).body(new ErrorResponseDTO("Id. '" + id + "' was not found."));
    }

    @Operation(summary = "Actualizar usuario", description = "Modifica los datos de un usuario existente según su ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuario actualizado correctamente."),
            @ApiResponse(responseCode = "404", description = "No se encontró un usuario con el ID indicado."),
            @ApiResponse(responseCode = "409", description = "El nombre de usuario ya está en uso.")
    })
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody @Valid UserDTO userDTO) {

        UserApp userApp = userAppMapper.toEntity(userDTO);
        userApp.setPassword(userAppService.encriptPassword(userApp.getPassword()));

        Set<Role> roleSet = new HashSet<>();
        for(Role role : userApp.getRoleList()) {
            roleService.findByIdRole(role.getId()).ifPresent(roleSet::add);
        }

        try {
            userApp.setRoleList(roleSet);
            UserResponseDTO userResponseDTO = userAppService.update(id, userApp);

            if(userResponseDTO != null) {
                return ResponseEntity.ok(userResponseDTO);
            }
            return ResponseEntity.notFound().build();

        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new ErrorResponseDTO("User '"+ userDTO.getUserName() +"' already exists."));
        }
    }

}
