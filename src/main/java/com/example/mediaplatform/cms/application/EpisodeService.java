package com.example.mediaplatform.cms.application;

import com.example.mediaplatform.cms.domain.model.Category;
import com.example.mediaplatform.cms.domain.model.Episode;
import com.example.mediaplatform.cms.domain.model.EpisodeStatus;
import com.example.mediaplatform.cms.domain.model.Program;
import com.example.mediaplatform.cms.domain.repository.CategoryRepository;
import com.example.mediaplatform.cms.domain.repository.EpisodeRepository;
import com.example.mediaplatform.cms.domain.repository.ProgramRepository;
import com.example.mediaplatform.cms.dto.CreateEpisodeRequest;
import com.example.mediaplatform.cms.dto.EpisodeResponse;
import com.example.mediaplatform.shared.events.EpisodePublishedEvent;
import com.example.mediaplatform.shared.exception.NotFoundException;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EpisodeService {

    private final EpisodeRepository episodeRepository;
    private final ProgramRepository programRepository;
    private final CategoryRepository categoryRepository;
    private final ApplicationEventPublisher eventPublisher;

    public EpisodeService(EpisodeRepository episodeRepository,
                          ProgramRepository programRepository,
                          CategoryRepository categoryRepository,
                          ApplicationEventPublisher eventPublisher) {
        this.episodeRepository = episodeRepository;
        this.programRepository = programRepository;
        this.categoryRepository = categoryRepository;
        this.eventPublisher = eventPublisher;
    }

    @Transactional
    public EpisodeResponse create(Long programId, CreateEpisodeRequest request) {
        Program program = programRepository.findById(programId)
                .orElseThrow(() -> new NotFoundException("Program not found: " + programId));

        Episode episode = new Episode();
        episode.setProgram(program);
        episode.setTitle(request.title());
        episode.setDescription(request.description());
        episode.setDurationSeconds(request.durationSeconds());
        episode.setLanguage(request.language());
        episode.setPublishDate(request.publishDate());
        episode.setSlug(request.slug());
        if (request.categoryIds() != null && !request.categoryIds().isEmpty()) {
            episode.getCategories().addAll(categoryRepository.findAllById(request.categoryIds()));
        }
        return toResponse(episodeRepository.save(episode));
    }

    @Transactional
    public void publish(Long episodeId) {
        Episode episode = episodeRepository.findWithProgramAndCategoriesById(episodeId)
                .orElseThrow(() -> new NotFoundException("Episode not found: " + episodeId));

        episode.setStatus(EpisodeStatus.PUBLISHED);
        if (episode.getPublishDate() == null) {
            episode.setPublishDate(java.time.Instant.now());
        }

        eventPublisher.publishEvent(new EpisodePublishedEvent(
                episode.getId(),
                episode.getProgram().getId(),
                episode.getProgram().getTitle(),
                episode.getTitle(),
                episode.getDescription(),
                episode.getProgram().getType().name(),
                episode.getLanguage(),
                episode.getDurationSeconds(),
                episode.getPublishDate(),
                episode.getSlug(),
                episode.getCategories().stream().map(Category::getName).toList()
        ));
    }

    @Transactional(readOnly = true)
    public List<EpisodeResponse> listByProgram(Long programId) {
        return episodeRepository.findByProgramId(programId).stream().map(this::toResponse).toList();
    }

    private EpisodeResponse toResponse(Episode episode) {
        return new EpisodeResponse(
                episode.getId(),
                episode.getProgram().getId(),
                episode.getTitle(),
                episode.getDescription(),
                episode.getDurationSeconds(),
                episode.getLanguage(),
                episode.getPublishDate(),
                episode.getSlug(),
                episode.getStatus(),
                episode.getCategories().stream().map(Category::getName).toList()
        );
    }
}
