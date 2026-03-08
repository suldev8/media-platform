package com.example.mediaplatform.cms.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.Instant;
import java.util.Set;

public record CreateEpisodeRequest(
        @NotBlank String title,
        String description,
        @NotNull @Min(1) Integer durationSeconds,
        @NotBlank String language,
        Instant publishDate,
        @NotBlank String slug,
        Set<Long> categoryIds
) {
}
