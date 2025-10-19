package com.cooperfilme.backend.repository;
import com.cooperfilme.backend.entity.VotoAprovador;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VotoRoteiroRepository extends JpaRepository<VotoAprovador, Long> {

    List<VotoAprovador> findByRoteiroId(Long roteiroId);

    boolean existsByRoteiroIdAndAprovadorId(Long roteiroId, Long aprovadorId);
}
