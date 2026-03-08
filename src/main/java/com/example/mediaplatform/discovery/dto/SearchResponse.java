package com.example.mediaplatform.discovery.dto;

import java.util.List;

public record SearchResponse(List<EpisodeView> items, long total) {
}
