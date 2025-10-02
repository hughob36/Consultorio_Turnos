package com.gestionTurno.controller;

import com.gestionTurno.dto.ErrorResponseDTO;
import com.gestionTurno.dto.SpecialistDTO;
import com.gestionTurno.dto.SpecialistResponseDTO;
import com.gestionTurno.dto.SuccessResponseDTO;
import com.gestionTurno.service.ISpecialistService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/specialist")
@PreAuthorize("denyAll")
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Especialistas", description = "Operaciones relacionadas con la gestión de especialistas.")
public class SpecialistController {

    @Autowired
    private ISpecialistService specialistService;

    @Operation(summary = "Listar especialistas", description = "Obtiene la lista completa de especialistas.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de especialistas obtenida correctamente.")
    })
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public ResponseEntity<List<SpecialistResponseDTO>> getSpecialists() {
        List<SpecialistResponseDTO> specialistList = specialistService.findAll();
        return ResponseEntity.ok(specialistList);
    }

    @Operation(summary = "Buscar especialista por ID", description = "Devuelve un especialista según su identificador único.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Especialista encontrado."),
            @ApiResponse(responseCode = "404", description = "No se encontró un especialista con el ID indicado.")
    })
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public ResponseEntity<SpecialistResponseDTO> getSpecialist(@PathVariable Long id) {

        Optional<SpecialistResponseDTO> specialistFound = specialistService.findById(id);
        return specialistFound
                .map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Crear especialista", description = "Registra un nuevo especialista en el sistema.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Especialista creado correctamente."),
            @ApiResponse(responseCode = "400", description = "Error en la solicitud o datos inválidos.")
    })
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> createSpecialist(@RequestBody @Valid SpecialistDTO specialistDTO) {

        SpecialistResponseDTO newSpecialist = specialistService.save(specialistDTO);

        if (newSpecialist != null) {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new SuccessResponseDTO("Specialist created.", newSpecialist));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponseDTO("Not created."));
    }

    @Operation(summary = "Eliminar especialista", description = "Elimina un especialista según su ID (baja lógica).")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Especialista eliminado correctamente."),
            @ApiResponse(responseCode = "404", description = "No se encontró un especialista con el ID indicado.")
    })
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<SpecialistResponseDTO> deleteSpecialistById(@PathVariable Long id) {
        Optional<SpecialistResponseDTO> specialistResponseDTO = specialistService.softDeleteById(id);
        return specialistResponseDTO.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Actualizar especialista", description = "Modifica los datos de un especialista existente según su ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Especialista actualizado correctamente."),
            @ApiResponse(responseCode = "404", description = "No se encontró un especialista con el ID indicado.")
    })
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateSpecialist(@PathVariable Long id, @RequestBody @Valid SpecialistDTO specialistDTO) {

        SpecialistDTO newSpecialistDTO = specialistService.updateByID(id, specialistDTO);
        if(newSpecialistDTO != null) {
            return ResponseEntity.status(HttpStatus.OK).body(new SuccessResponseDTO("Modified.", newSpecialistDTO));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponseDTO("It was not modified."));
    }
}


