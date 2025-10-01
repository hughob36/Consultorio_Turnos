package com.gestionTurno.controller;

import com.gestionTurno.dto.ErrorResponseDTO;
import com.gestionTurno.dto.PermissionDTO;
import com.gestionTurno.dto.PermissionResponseDTO;
import com.gestionTurno.service.IPermissionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/permission")
@Tag(name = "Permisos", description = "Operaciones relacionadas con la gestión de permisos de usuario.")
public class PermissionController {

    @Autowired
    private IPermissionService permissionService;

    @Operation(summary = "Listar permisos", description = "Obtiene la lista completa de permisos disponibles.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de permisos obtenida correctamente.")
    })
    @GetMapping
    public ResponseEntity<List<PermissionResponseDTO>> getPermissions() {
        List<PermissionResponseDTO> permissionList = permissionService.findAll();
        return ResponseEntity.ok(permissionList);
    }

    @Operation(summary = "Buscar permiso por ID",
              description = "Devuelve un permiso específico según su identificador único.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Permiso encontrado."),
            @ApiResponse(responseCode = "404", description = "No se encontró un permiso con el ID indicado.")
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> getPermission(@PathVariable Long id) {
        Optional<PermissionResponseDTO> permissionFound = permissionService.findByid(id);
        return permissionFound
                .map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Crear un nuevo permiso",
              description = "Registra un nuevo permiso en la base de datos. Si el nombre ya existe, devuelve error de conflicto.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Permiso creado exitosamente."),
            @ApiResponse(responseCode = "409", description = "El permiso ya existe en la base de datos.")
    })
    @PostMapping
    public ResponseEntity<?> createPermission(@RequestBody @Valid PermissionDTO permissionDTO) {

        try {
            PermissionResponseDTO newPermission = permissionService.save(permissionDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(newPermission);

        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new ErrorResponseDTO("permission '" + permissionDTO.getPermissionName() + "' already exists."));
        }
    }

    @Operation(summary = "Eliminar un permiso",
              description = "Elimina un permiso existente según su ID. Devuelve error si no se encuentra.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Permiso eliminado correctamente."),
            @ApiResponse(responseCode = "404", description = "No se encontró un permiso con el ID indicado.")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePermission(@PathVariable Long id) {

        if(permissionService.deleteById(id)) {
            return ResponseEntity.noContent().build();
         }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponseDTO("Id. '"+ id +"' was not found."));
    }

    @Operation(summary = "Actualizar un permiso",
              description = "Modifica los datos de un permiso existente. Devuelve error si no se encuentra o si el nombre ya está en uso.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Permiso actualizado correctamente."),
            @ApiResponse(responseCode = "404", description = "No se encontró un permiso con el ID indicado."),
            @ApiResponse(responseCode = "409", description = "El nombre del permiso ya está en uso.")
    })
    @PutMapping("/{id}")
    public ResponseEntity<?> updatePermission(@PathVariable Long id, @RequestBody @Valid PermissionDTO permissionDTO) {

        try {
            PermissionResponseDTO newPermission = permissionService.update(id, permissionDTO);

            if(newPermission != null) {
                return ResponseEntity.ok(newPermission);
            }
            return ResponseEntity.notFound().build();

        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new ErrorResponseDTO("Permission '"+ permissionDTO.getPermissionName() +"' already exists."));
        }

    }
}
