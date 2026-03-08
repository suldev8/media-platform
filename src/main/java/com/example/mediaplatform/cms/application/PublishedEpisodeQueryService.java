package com.example.mediaplatform.cms.application;

import com.example.mediaplatform.cms.domain.model.Category;
import com.example.mediaplatform.cms.domain.model.Episode;
import com.example.mediaplatform.cms.domain.model.EpisodeStatus;
import com.example.mediaplatform.cms.domain.repository.EpisodeRepository;
import com.example.mediaplatform.shared.events.EpisodePublishedEvent;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PublishedEpisodeQueryService {

    private final EpisodeRepository episodeRepository;

    public PublishedEpisodeQueryService(EpisodeRepository episodeRepository) {
        this.episodeRepository = episodeRepository;
    }

    @Transactional(readOnly = true)
    public List<EpisodePublishedEvent> listPublishedEpisodes() {
        return episodeRepository.findByStatus(EpisodeStatus.PUBLISHED)
                .stream()
                .map(this::toEvent)
                .toList();
    }

    private EpisodePublishedEvent toEvent(Episode episode) {
        return new EpisodePublishedEvent(
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
        );
    }
}
