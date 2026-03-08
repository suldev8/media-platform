package com.example.mediaplatform.cms.dto;

import com.example.mediaplatform.cms.domain.model.ContentType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateProgramRequest(
        @NotBlank String title,
        String description,
        @NotNull ContentType type,
        @NotBlank String language,
        @NotBlank String slug
) {
}
