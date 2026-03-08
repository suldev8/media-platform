package com.example.mediaplatform.cms.api;

import com.example.mediaplatform.cms.application.ProgramService;
import com.example.mediaplatform.cms.dto.CreateProgramRequest;
import com.example.mediaplatform.cms.dto.ProgramResponse;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@PreAuthorize("hasAnyRole('EDITOR', 'ADMIN')")
@RequestMapping("/api/v1/admin/programs")
public class ProgramController {

    private final ProgramService programService;

    public ProgramController(ProgramService programService) {
        this.programService = programService;
    }

    @PostMapping
    @Operation(summary = "Create a program in CMS")
    public ProgramResponse create(@Valid @RequestBody CreateProgramRequest request) {
        return programService.create(request);
    }

    @GetMapping("/{id}")
    public ProgramResponse get(@PathVariable Long id) {
        return programService.get(id);
    }

    @GetMapping
    public List<ProgramResponse> list() {
        return programService.list();
    }
}
