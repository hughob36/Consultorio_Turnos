package com.gestionTurno.controller;

import com.gestionTurno.dto.*;
import com.gestionTurno.model.Permission;
import com.gestionTurno.service.IPermissionService;
import com.gestionTurno.service.IRoleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
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
@RequestMapping("/api/role")
@PreAuthorize("denyAll")
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Roles", description = "Operaciones relacionadas con la gestión de roles y permisos.")
public class RoleController {

    @Autowired
    private IRoleService roleService;

    @Autowired
    private IPermissionService permissionService;

    @Operation(summary = "Listar roles", description = "Obtiene la lista completa de roles registrados.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de roles obtenida correctamente.")
    })
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<RoleResponseDTO>> getRoles() {

        List<RoleResponseDTO> roleList = roleService.findAll();
        return ResponseEntity.ok(roleList);
    }

    @Operation(summary = "Buscar rol por ID", description = "Devuelve un rol según su identificador único.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Rol encontrado."),
            @ApiResponse(responseCode = "404", description = "No se encontró un rol con el ID indicado.")
    })
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<RoleResponseDTO> getRole(@PathVariable Long id) {

        Optional<RoleResponseDTO> roleFound = roleService.findById(id);
        return roleFound
                .map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Crear rol", description = "Registra un nuevo rol con permisos asignados.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Rol creado correctamente."),
            @ApiResponse(responseCode = "409", description = "El rol ya existe o hay permisos duplicados.")
    })
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<?> createRole(@RequestBody @Valid RoleDTO roleDTO) {

        Set<Permission> permissionSet = new HashSet<>();
        for(Permission permission : roleDTO.getPermissionList()) {
            permissionService.findByIdPermission(permission.getId()).ifPresent(permissionSet::add);
        }

        try {
            roleDTO.setPermissionList(permissionSet);
            RoleResponseDTO newRole = roleService.save(roleDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(newRole);

        } catch (DataIntegrityViolationException e) {

            return ResponseEntity
                    .status(HttpStatus.CONFLICT).body(new ErrorResponseDTO("Role '" + roleDTO.getRole() + "' already exists or duplicate permissions."));
        }
    }

    @Operation(summary = "Eliminar rol", description = "Elimina un rol existente según su ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Rol eliminado correctamente."),
            @ApiResponse(responseCode = "404", description = "No se encontró un rol con el ID indicado.")
    })
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteRoleById(@PathVariable Long id) {

        boolean delete = roleService.deleteById(id);

        if(delete) return ResponseEntity.noContent().build();

         return ResponseEntity.status(HttpStatus.NOT_FOUND)
                 .body(new ErrorResponseDTO("Id. '"+ id +"' not found."));
    }

    @Operation(summary = "Actualizar rol", description = "Modifica los datos de un rol existente según su ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Rol actualizado correctamente."),
            @ApiResponse(responseCode = "404", description = "No se encontró un rol con el ID indicado"),
            @ApiResponse(responseCode = "409", description = "El nombre del rol ya está en uso.")
    })
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<?> updateRole(@PathVariable Long id, @RequestBody @Valid RoleDTO roleDTO) {

        Set<Permission> permissionList = new HashSet<>();
        for(Permission permission : roleDTO.getPermissionList()) {
            permissionService.findByIdPermission(permission.getId()).ifPresent(permissionList::add);
        }

        try {

            roleDTO.setPermissionList(permissionList);
            RoleResponseDTO newRole = roleService.update(id, roleDTO);

            if(newRole != null) {
                return ResponseEntity.ok(newRole);
            }
            return ResponseEntity.notFound().build();

        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new ErrorResponseDTO("Role '"+ roleDTO.getRole() +"' already exists."));
        }
    }

}
