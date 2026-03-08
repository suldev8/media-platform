package com.example.mediaplatform.discovery.application;

import com.example.mediaplatform.discovery.domain.document.EpisodeDocument;
import com.example.mediaplatform.discovery.dto.EpisodeView;
import com.example.mediaplatform.discovery.dto.SearchResponse;
import com.example.mediaplatform.discovery.infrastructure.repository.EpisodeSearchRepository;
import com.example.mediaplatform.shared.exception.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DiscoveryService {

    private final EpisodeSearchRepository repository;

    public DiscoveryService(EpisodeSearchRepository repository) {
        this.repository = repository;
    }

    public SearchResponse search(String q) {
        List<EpisodeView> matches = new ArrayList<>();
        repository.search(q).forEach(doc -> matches.add(toView(doc)));
        return new SearchResponse(matches, matches.size());
    }

    public EpisodeView getBySlug(String slug) {
        EpisodeDocument document = repository.findBySlug(slug)
                .orElseThrow(() -> new NotFoundException("Episode not found for slug: " + slug));
        return toView(document);
    }

    private EpisodeView toView(EpisodeDocument doc) {
        return new EpisodeView(doc.getEpisodeId(), doc.getProgramId(), doc.getProgramTitle(), doc.getEpisodeTitle(),
                doc.getDescription(), doc.getType(), doc.getLanguage(), doc.getDurationSeconds(),
                doc.getPublishDate(), doc.getSlug(), doc.getCategories());
    }
}
