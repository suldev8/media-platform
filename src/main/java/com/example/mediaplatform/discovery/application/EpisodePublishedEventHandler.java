package com.example.mediaplatform.discovery.application;

import com.example.mediaplatform.discovery.domain.document.EpisodeDocument;
import com.example.mediaplatform.discovery.infrastructure.repository.EpisodeSearchRepository;
import com.example.mediaplatform.shared.events.EpisodePublishedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class EpisodePublishedEventHandler {

    private final EpisodeSearchRepository repository;

    public EpisodePublishedEventHandler(EpisodeSearchRepository repository) {
        this.repository = repository;
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handle(EpisodePublishedEvent event) {
        index(event);
    }

    public void index(EpisodePublishedEvent event) {
        EpisodeDocument doc = new EpisodeDocument();
        doc.setEpisodeId(event.episodeId());
        doc.setProgramId(event.programId());
        doc.setProgramTitle(event.programTitle());
        doc.setEpisodeTitle(event.episodeTitle());
        doc.setDescription(event.description());
        doc.setType(event.type());
        doc.setLanguage(event.language());
        doc.setDurationSeconds(event.durationSeconds());
        doc.setPublishDate(event.publishDate());
        doc.setSlug(event.slug());
        doc.setCategories(event.categories());
        repository.save(doc);
    }
}
