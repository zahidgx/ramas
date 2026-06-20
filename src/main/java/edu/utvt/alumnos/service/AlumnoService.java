package edu.utvt.alumnos.service;

import edu.utvt.alumnos.dto.request.AlumnoRequest;
import edu.utvt.alumnos.dto.response.AlumnoResponse;

import java.util.List;

public interface AlumnoService {
    AlumnoResponse create(AlumnoRequest alumnoRequest);
    List<AlumnoResponse> findAll();
    AlumnoResponse findById(Long id);
    void update(Long id, AlumnoRequest alumnoRequest);
    void delete(Long id);
}
