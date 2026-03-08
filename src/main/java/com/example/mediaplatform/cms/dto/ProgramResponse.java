package com.example.mediaplatform.cms.dto;

import com.example.mediaplatform.cms.domain.model.ContentType;
import com.example.mediaplatform.cms.domain.model.ProgramStatus;

public record ProgramResponse(Long id, String title, String description, ContentType type, String language, String slug,
                              ProgramStatus status) {
}
