package edu.utvt.alumnos.service.impl;

import edu.utvt.alumnos.dto.request.AlumnoRequest;
import edu.utvt.alumnos.dto.response.AlumnoResponse;
import edu.utvt.alumnos.entity.Alumno;
import edu.utvt.alumnos.exception.AlumnoNotFoundException;
import edu.utvt.alumnos.exception.CorreoElectronicoAlreadyExistsException;
import edu.utvt.alumnos.exception.MatriculaAlreadyExistsException;
import edu.utvt.alumnos.mapper.AlumnoMapper;
import edu.utvt.alumnos.repository.AlumnoRepository;
import edu.utvt.alumnos.service.AlumnoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AlumnoServiceImpl implements AlumnoService {

    private final AlumnoRepository alumnoRepository;
    private final AlumnoMapper alumnoMapper;

    @Override
    @Transactional
    public AlumnoResponse create(AlumnoRequest alumnoRequest) {
        log.info("Iniciando proceso de creación de alumno con matrícula: {}", alumnoRequest.matricula());

        if (alumnoRepository.existsByMatricula(alumnoRequest.matricula())) {
            log.error("La matrícula {} ya existe", alumnoRequest.matricula());
            throw new MatriculaAlreadyExistsException("La matrícula " + alumnoRequest.matricula() + " ya está en uso.");
        }

        if (alumnoRepository.existsByCorreoElectronico(alumnoRequest.correoElectronico())) {
            log.error("El correo electrónico {} ya existe", alumnoRequest.correoElectronico());
            throw new CorreoElectronicoAlreadyExistsException("El correo electrónico " + alumnoRequest.correoElectronico() + " ya está en uso.");
        }

        Alumno alumno = alumnoMapper.toEntity(alumnoRequest);
        alumno = alumnoRepository.save(alumno);
        log.info("Alumno creado exitosamente con ID: {}", alumno.getId());

        return alumnoMapper.toResponse(alumno);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AlumnoResponse> findAll() {
        log.info("Consultando todos los alumnos activos");
        List<Alumno> alumnos = alumnoRepository.findAllByActivoTrue();
        return alumnoMapper.toResponse(alumnos);
    }

    @Override
    @Transactional(readOnly = true)
    public AlumnoResponse findById(Long id) {
        log.info("Buscando alumno con ID: {}", id);
        return alumnoRepository.findByIdAndActivoTrue(id)
                .map(alumnoMapper::toResponse)
                .orElseThrow(() -> {
                    log.error("No se encontró un alumno activo con ID: {}", id);
                    return new AlumnoNotFoundException("No se encontró un alumno activo con el ID: " + id);
                });
    }

    @Override
    @Transactional
    public void update(Long id, AlumnoRequest alumnoRequest) {
        log.info("Iniciando actualización para el alumno con ID: {}", id);
        Alumno alumno = alumnoRepository.findByIdAndActivoTrue(id)
                .orElseThrow(() -> {
                    log.error("No se encontró un alumno activo para actualizar con ID: {}", id);
                    return new AlumnoNotFoundException("No se encontró un alumno activo con el ID: " + id);
                });

        // Check for email uniqueness if it's being changed
        if (!alumno.getCorreoElectronico().equals(alumnoRequest.correoElectronico()) &&
            alumnoRepository.existsByCorreoElectronico(alumnoRequest.correoElectronico())) {
            log.error("El nuevo correo electrónico {} ya está en uso", alumnoRequest.correoElectronico());
            throw new CorreoElectronicoAlreadyExistsException("El correo electrónico " + alumnoRequest.correoElectronico() + " ya está en uso.");
        }

        alumnoMapper.updateEntity(alumno, alumnoRequest);
        alumnoRepository.save(alumno);
        log.info("Alumno con ID: {} actualizado correctamente", id);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        log.info("Iniciando baja lógica para el alumno con ID: {}", id);
        Alumno alumno = alumnoRepository.findByIdAndActivoTrue(id)
                .orElseThrow(() -> {
                    log.error("No se encontró un alumno activo para eliminar con ID: {}", id);
                    return new AlumnoNotFoundException("No se encontró un alumno activo con el ID: " + id);
                });

        alumno.setActivo(false);
        alumnoRepository.save(alumno);
        log.info("Baja lógica aplicada correctamente al alumno con ID: {}", id);
    }
}
