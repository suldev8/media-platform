package com.example.mediaplatform.discovery.application;

import com.example.mediaplatform.cms.application.PublishedEpisodeQueryService;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class InitialSearchIndexLoader implements ApplicationRunner {

    private final PublishedEpisodeQueryService publishedEpisodeQueryService;
    private final EpisodePublishedEventHandler episodePublishedEventHandler;

    public InitialSearchIndexLoader(PublishedEpisodeQueryService publishedEpisodeQueryService,
                                    EpisodePublishedEventHandler episodePublishedEventHandler) {
        this.publishedEpisodeQueryService = publishedEpisodeQueryService;
        this.episodePublishedEventHandler = episodePublishedEventHandler;
    }

    @Override
    public void run(ApplicationArguments args) {
        publishedEpisodeQueryService.listPublishedEpisodes()
                .forEach(episodePublishedEventHandler::index);
    }
}
