package edu.utvt.alumnos.repository;

import edu.utvt.alumnos.entity.Alumno;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AlumnoRepository extends JpaRepository<Alumno, Long> {

    Optional<Alumno> findByIdAndActivoTrue(Long id);

    List<Alumno> findAllByActivoTrue();

    boolean existsByMatricula(String matricula);

    boolean existsByCorreoElectronico(String correoElectronico);
}
