package com.example.mediaplatform.cms.domain.repository;

import com.example.mediaplatform.cms.domain.model.Episode;
import com.example.mediaplatform.cms.domain.model.EpisodeStatus;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EpisodeRepository extends JpaRepository<Episode, Long> {
    @EntityGraph(attributePaths = {"program", "categories"})
    Optional<Episode> findWithProgramAndCategoriesById(Long id);

    @EntityGraph(attributePaths = {"program", "categories"})
    List<Episode> findByProgramId(Long programId);

    @EntityGraph(attributePaths = {"program", "categories"})
    List<Episode> findByStatus(EpisodeStatus status);
}
