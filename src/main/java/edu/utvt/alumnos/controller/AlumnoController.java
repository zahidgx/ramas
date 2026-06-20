package edu.utvt.alumnos.controller;

import edu.utvt.alumnos.dto.request.AlumnoRequest;
import edu.utvt.alumnos.dto.response.ApiResponse;
import edu.utvt.alumnos.dto.response.AlumnoResponse;
import edu.utvt.alumnos.service.AlumnoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/alumnos")
@RequiredArgsConstructor
public class AlumnoController {

    private final AlumnoService alumnoService;

    @Operation(summary = "Registrar un nuevo alumno")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "Alumno registrado exitosamente",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Solicitud inválida"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "409", description = "Conflicto, la matrícula o correo ya existen")
    })
    @PostMapping
    public ResponseEntity<ApiResponse<AlumnoResponse>> create(@Valid @RequestBody AlumnoRequest alumnoRequest) {
        AlumnoResponse alumnoResponse = alumnoService.create(alumnoRequest);
        ApiResponse<AlumnoResponse> response = ApiResponse.<AlumnoResponse>builder()
                .success(true)
                .message("Alumno registrado exitosamente")
                .data(alumnoResponse)
                .build();
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Operation(summary = "Consultar todos los alumnos activos")
    @GetMapping
    public ResponseEntity<ApiResponse<List<AlumnoResponse>>> findAll() {
        List<AlumnoResponse> alumnos = alumnoService.findAll();
        ApiResponse<List<AlumnoResponse>> response = ApiResponse.<List<AlumnoResponse>>builder()
                .success(true)
                .message("Lista de alumnos activos")
                .data(alumnos)
                .build();
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Consultar un alumno por su ID")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Alumno encontrado"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Alumno no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<AlumnoResponse>> findById(@PathVariable Long id) {
        AlumnoResponse alumno = alumnoService.findById(id);
        ApiResponse<AlumnoResponse> response = ApiResponse.<AlumnoResponse>builder()
                .success(true)
                .message("Alumno encontrado")
                .data(alumno)
                .build();
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Actualizar los datos de un alumno")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Alumno actualizado"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Alumno no encontrado"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "409", description = "Conflicto, el correo ya existe")
    })
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> update(@PathVariable Long id, @Valid @RequestBody AlumnoRequest alumnoRequest) {
        alumnoService.update(id, alumnoRequest);
        ApiResponse<Void> response = ApiResponse.<Void>builder()
                .success(true)
                .message("Alumno actualizado correctamente")
                .build();
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Eliminar un alumno (Baja lógica)")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Alumno dado de baja"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Alumno no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        alumnoService.delete(id);
        ApiResponse<Void> response = ApiResponse.<Void>builder()
                .success(true)
                .message("Alumno dado de baja correctamente")
                .build();
        return ResponseEntity.ok(response);
    }
}
