package com.example.mediaplatform.cms.domain.repository;

import com.example.mediaplatform.cms.domain.model.Program;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProgramRepository extends JpaRepository<Program, Long> {
    Optional<Program> findBySlug(String slug);
}
