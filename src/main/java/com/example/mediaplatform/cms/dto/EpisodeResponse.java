package com.example.mediaplatform.cms.dto;

import com.example.mediaplatform.cms.domain.model.EpisodeStatus;

import java.time.Instant;
import java.util.List;

public record EpisodeResponse(Long id, Long programId, String title, String description, Integer durationSeconds,
                              String language, Instant publishDate, String slug, EpisodeStatus status,
                              List<String> categories) {
}
