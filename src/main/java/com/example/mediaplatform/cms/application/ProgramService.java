package com.example.mediaplatform.cms.application;

import com.example.mediaplatform.cms.domain.model.Program;
import com.example.mediaplatform.cms.domain.model.ProgramStatus;
import com.example.mediaplatform.cms.domain.repository.ProgramRepository;
import com.example.mediaplatform.cms.dto.CreateProgramRequest;
import com.example.mediaplatform.cms.dto.ProgramResponse;
import com.example.mediaplatform.shared.exception.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProgramService {

    private final ProgramRepository programRepository;

    public ProgramService(ProgramRepository programRepository) {
        this.programRepository = programRepository;
    }

    @Transactional
    public ProgramResponse create(CreateProgramRequest request) {
        Program program = new Program();
        program.setTitle(request.title());
        program.setDescription(request.description());
        program.setType(request.type());
        program.setLanguage(request.language());
        program.setSlug(request.slug());
        program.setStatus(ProgramStatus.DRAFT);
        return toResponse(programRepository.save(program));
    }

    @Transactional(readOnly = true)
    public ProgramResponse get(Long id) {
        return toResponse(programRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Program not found: " + id)));
    }

    @Transactional(readOnly = true)
    public List<ProgramResponse> list() {
        return programRepository.findAll().stream().map(this::toResponse).toList();
    }

    private ProgramResponse toResponse(Program program) {
        return new ProgramResponse(program.getId(), program.getTitle(), program.getDescription(), program.getType(),
                program.getLanguage(), program.getSlug(), program.getStatus());
    }
}
