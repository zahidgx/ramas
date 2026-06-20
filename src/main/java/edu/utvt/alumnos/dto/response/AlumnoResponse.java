package edu.utvt.alumnos.dto.response;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record AlumnoResponse(
        Long id,
        String matricula,
        String nombre,
        String apellidoPaterno,
        String apellidoMaterno,
        String correoElectronico,
        String telefono,
        LocalDate fechaNacimiento,
        String carrera,
        Integer semestre,
        boolean activo,
        LocalDateTime fechaRegistro
) {
}
