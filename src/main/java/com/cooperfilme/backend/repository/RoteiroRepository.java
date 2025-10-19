package com.cooperfilme.backend.repository;

import com.cooperfilme.backend.entity.Roteiro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface RoteiroRepository extends JpaRepository<Roteiro, Long>, JpaSpecificationExecutor<Roteiro> {
    Optional<Roteiro> findByProtocolo(String protocolo);

}