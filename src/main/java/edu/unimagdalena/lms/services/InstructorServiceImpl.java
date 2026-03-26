package edu.unimagdalena.lms.services;

import edu.unimagdalena.lms.api.dto.InstructorDtos.*;
import edu.unimagdalena.lms.repositories.InstructorRepository;
import edu.unimagdalena.lms.services.mapper.IInstructorMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class InstructorServiceImpl implements InstructorService {

    private final InstructorRepository repo;
    private final IInstructorMapper mapper;

    @Transactional
    @Override
    public InstructorResponse create(InstructorCreateRequest req) {
        var entity = mapper.toEntity(req);
        entity.setCreatedAt(Instant.now());
        entity.setUpdatedAt(Instant.now());
        if (req.profile() != null) {
            var profile = mapper.toEntity(req.profile());
            profile.setInstructor(entity);
            entity.setInstructorProfile(profile);
        }
        var saved = repo.save(entity);
        return mapper.toResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public InstructorResponse get(Long id) {
        return repo.findById(id)
                .map(i -> mapper.toResponse(i))
                .orElseThrow(() -> new RuntimeException("Instructor %d not found".formatted(id)));
    }

    @Override
    @Transactional(readOnly = true)
    public List<InstructorResponse> list() {
        return repo.findAll().stream()
                .map(i -> mapper.toResponse(i))
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<InstructorResponse> getByEmail(String email) {
        return repo.findByEmailIgnoreCase(email)
                .stream()
                .map(i -> mapper.toResponse(i))
                .toList();
    }

    @Override
    @Transactional
    public InstructorResponse update(Long id, InstructorUpdateRequest req) {
        var entity = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Instructor %d not found".formatted(id)));
        mapper.patch(entity, req);
        entity.setUpdatedAt(Instant.now());
        return mapper.toResponse(repo.save(entity));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        repo.deleteById(id);
    }
}