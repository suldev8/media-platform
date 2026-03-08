package com.example.mediaplatform.discovery.dto;

import java.time.Instant;
import java.util.List;

public record EpisodeView(Long episodeId, Long programId, String programTitle, String episodeTitle,
                          String description, String type, String language, Integer durationSeconds,
                          Instant publishDate, String slug, List<String> categories) {
}
