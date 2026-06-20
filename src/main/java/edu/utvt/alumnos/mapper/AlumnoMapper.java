package edu.utvt.alumnos.mapper;

import edu.utvt.alumnos.dto.request.AlumnoRequest;
import edu.utvt.alumnos.dto.response.AlumnoResponse;
import edu.utvt.alumnos.entity.Alumno;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AlumnoMapper {

    AlumnoResponse toResponse(Alumno alumno);

    List<AlumnoResponse> toResponse(List<Alumno> alumnos);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "activo", ignore = true)
    @Mapping(target = "fechaRegistro", ignore = true)
    Alumno toEntity(AlumnoRequest alumnoRequest);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "activo", ignore = true)
    @Mapping(target = "fechaRegistro", ignore = true)
    @Mapping(target = "matricula", ignore = true) // Matricula should not be updatable
    void updateEntity(@MappingTarget Alumno alumno, AlumnoRequest alumnoRequest);
}
