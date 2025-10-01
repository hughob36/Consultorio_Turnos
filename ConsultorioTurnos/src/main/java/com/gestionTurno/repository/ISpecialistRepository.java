package com.gestionTurno.repository;

import com.gestionTurno.model.Specialist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ISpecialistRepository extends JpaRepository<Specialist, Long> {
}
