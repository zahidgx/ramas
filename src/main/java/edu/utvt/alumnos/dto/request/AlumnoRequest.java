package edu.utvt.alumnos.dto.request;

import jakarta.validation.constraints.*;

import java.time.LocalDate;

public record AlumnoRequest(
        @NotBlank(message = "La matrícula no puede estar vacía")
        @Pattern(regexp = "^[a-zA-Z0-9-]+$", message = "La matrícula debe ser alfanumérica")
        String matricula,

        @NotBlank(message = "El nombre no puede estar vacío")
        String nombre,

        @NotBlank(message = "El apellido paterno no puede estar vacío")
        String apellidoPaterno,

        String apellidoMaterno,

        @NotBlank(message = "El correo electrónico no puede estar vacío")
        @Email(message = "El formato del correo electrónico no es válido")
        String correoElectronico,

        @Pattern(regexp = "^\\d{10}$", message = "El teléfono debe tener 10 dígitos")
        String telefono,

        @NotNull(message = "La fecha de nacimiento no puede ser nula")
        @Past(message = "La fecha de nacimiento debe ser en el pasado")
        LocalDate fechaNacimiento,

        @NotBlank(message = "La carrera no puede estar vacía")
        String carrera,

        @NotNull(message = "El semestre no puede ser nulo")
        @Min(value = 1, message = "El semestre debe ser mayor a 0")
        @Max(value = 10, message = "El semestre no puede ser mayor a 10")
        Integer semestre
) {
}
