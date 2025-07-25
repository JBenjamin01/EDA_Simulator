package com.eda.backend.repositories;

import com.eda.backend.models.EstructuraGuardada;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EstructuraGuardadaRepository extends JpaRepository<EstructuraGuardada, Long> {
    List<EstructuraGuardada> findByTipo(String tipo);
}
