package com.cooperfilme.backend.repository;

import com.cooperfilme.backend.entity.Status;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StatusRepository extends JpaRepository<Status, Long> { }
