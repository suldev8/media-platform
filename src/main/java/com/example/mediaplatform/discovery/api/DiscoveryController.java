package com.example.mediaplatform.discovery.api;

import com.example.mediaplatform.discovery.application.DiscoveryService;
import com.example.mediaplatform.discovery.dto.EpisodeView;
import com.example.mediaplatform.discovery.dto.SearchResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/discovery")
public class DiscoveryController {

    private final DiscoveryService discoveryService;

    public DiscoveryController(DiscoveryService discoveryService) {
        this.discoveryService = discoveryService;
    }

    @GetMapping("/episodes/search")
    public SearchResponse search(@RequestParam String q) {
        return discoveryService.search(q);
    }

    @GetMapping("/episodes/{slug}")
    public EpisodeView getBySlug(@PathVariable String slug) {
        return discoveryService.getBySlug(slug);
    }
}
