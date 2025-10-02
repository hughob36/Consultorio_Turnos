package com.gestionTurno.controller;

import com.gestionTurno.dto.AppointmentDTO;
import com.gestionTurno.dto.AppointmentResponseDTO;
import com.gestionTurno.dto.ErrorResponseDTO;
import com.gestionTurno.dto.SuccessResponseDTO;
import com.gestionTurno.model.AppointmentStatus;
import com.gestionTurno.service.IAppointmentService;
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
@RequestMapping("/api/appointment")
@PreAuthorize("denyAll")
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Turnos", description = "Operaciones relacionadas con la gestión de turnos.")
public class AppointmentController {

    @Autowired
    private IAppointmentService appointmentService;

    @Operation(summary = "Listar turnos", description = "Devuelve todos los turnos registrados.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de turnos obtenida correctamente.")
    })
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public ResponseEntity<List<AppointmentResponseDTO>> getAllAppointments() {
       List<AppointmentResponseDTO> appointmentList = appointmentService.findAll();
        return ResponseEntity.ok(appointmentList);
    }

    @Operation(summary = "Listar un turno", description = "Devuelve un turno registrado.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Turno encontrado."),
            @ApiResponse(responseCode = "404", description = "No se encontró el turno con el ID indicado.")
    })
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public ResponseEntity<AppointmentResponseDTO> getAppointment(@PathVariable Long id) {

        Optional<AppointmentResponseDTO> appointmentFound = appointmentService.findById(id);
        return appointmentFound
                    .map(ResponseEntity::ok)
                    .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Crear un turno", description = "Crea un nuevo turno para un especialista.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Turno creado exitosamente."),
            @ApiResponse(responseCode = "400", description = "Error en la solicitud o datos inválidos.")
    })
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> createAppointment(@RequestBody @Valid AppointmentDTO appointmentDTO) {

        AppointmentResponseDTO newAppointment = appointmentService.save(appointmentDTO);

        if(newAppointment != null) {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new SuccessResponseDTO("Created shift.", newAppointment));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponseDTO("Not created."));
    }

    @Operation(summary = "Modifica estado", description = "Modifica el estado actual de un turno.")
    @ApiResponses({
            @ApiResponse(responseCode = "202", description = "Estado del turno modificado correctamente."),
            @ApiResponse(responseCode = "404", description = "No se encontró el turno con el ID indicado.")
    })
    @PatchMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateAppointmentStatusById(@PathVariable Long id,
                                                         @RequestBody AppointmentStatus status) {
        AppointmentResponseDTO appointmentNewStatus = appointmentService.updateAppointmentById(id, status);

        if(appointmentNewStatus != null) {
            return ResponseEntity.status(HttpStatus.ACCEPTED)
                    .body(new SuccessResponseDTO("New status.", appointmentNewStatus));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponseDTO("NO status was changed."));
    }

    @Operation(summary = "Modifica un turno", description = "Modifica un turno registrado.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Turno actualizado correctamente."),
            @ApiResponse(responseCode = "404", description = "No se encontró el turno con el ID indicado.")
    })
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateAppointment(@PathVariable Long id,
                                               @RequestBody @Valid AppointmentDTO appointmentDTO) {

        AppointmentDTO updateAppointment = appointmentService.updateAppointment(id, appointmentDTO);
        if(updateAppointment != null) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new SuccessResponseDTO("Modified.", updateAppointment));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponseDTO("Not found."));
    }

}
