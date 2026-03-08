package com.example.mediaplatform.cms.api;

import com.example.mediaplatform.cms.application.EpisodeService;
import com.example.mediaplatform.cms.dto.CreateEpisodeRequest;
import com.example.mediaplatform.cms.dto.EpisodeResponse;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@PreAuthorize("hasAnyRole('EDITOR', 'ADMIN')")
@RequestMapping("/api/v1/admin/programs/{programId}/episodes")
public class EpisodeController {

    private final EpisodeService episodeService;

    public EpisodeController(EpisodeService episodeService) {
        this.episodeService = episodeService;
    }

    @PostMapping
    public EpisodeResponse create(@PathVariable Long programId, @Valid @RequestBody CreateEpisodeRequest request) {
        return episodeService.create(programId, request);
    }

    @GetMapping
    public List<EpisodeResponse> list(@PathVariable Long programId) {
        return episodeService.listByProgram(programId);
    }

    @PostMapping("/{episodeId}/publish")
    public void publish(@PathVariable Long programId, @PathVariable Long episodeId) {
        episodeService.publish(episodeId);
    }
}
