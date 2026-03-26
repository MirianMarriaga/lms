package edu.unimagdalena.lms.services;

import edu.unimagdalena.lms.api.dto.CourseDtos.*;
import edu.unimagdalena.lms.repositories.CourseRepository;
import edu.unimagdalena.lms.repositories.InstructorRepository;
import edu.unimagdalena.lms.services.mapper.ICourseMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CourseServiceImpl implements CourseService {

    private final CourseRepository repo;
    private final InstructorRepository instructorRepo;
    private final ICourseMapper mapper;

    @Transactional
    @Override
    public CourseResponse create(CourseCreateRequest req) {
        var instructor = instructorRepo.findById(req.instructorId())
                .orElseThrow(() -> new RuntimeException("Instructor %d not found".formatted(req.instructorId())));
        var entity = mapper.toEntity(req);
        entity.setInstructor(instructor);
        entity.setCreatedAt(Instant.now());
        entity.setUpdatedAt(Instant.now());
        return mapper.toResponse(repo.save(entity));
    }

    @Override
    @Transactional(readOnly = true)
    public CourseResponse get(Long id) {
        return repo.findById(id)
                .map(c -> mapper.toResponse(c))
                .orElseThrow(() -> new RuntimeException("Course %d not found".formatted(id)));
    }

    @Override
    @Transactional(readOnly = true)
    public List<CourseResponse> list() {
        return repo.findAll().stream()
                .map(c -> mapper.toResponse(c))
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<CourseResponse> getByTitle(String title) {
        return repo.findByTitleIgnoreCase(title).stream()
                .map(c -> mapper.toResponse(c))
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<CourseResponse> getByStatus(String status) {
        return repo.findByStatus(status).stream()
                .map(c -> mapper.toResponse(c))
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<CourseResponse> getByActive(Boolean active) {
        return repo.findByActiveTrue().stream()
                .map(c -> mapper.toResponse(c))
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<CourseResponse> getByInstructorId(Long idInstructor) {
        return repo.findByInstructorId(idInstructor).stream()
                .map(c -> mapper.toResponse(c))
                .toList();
    }

    @Override
    @Transactional
    public CourseResponse update(Long id, CourseUpdateRequest req) {
        var entity = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Course %d not found".formatted(id)));
        if (req.instructorId() != null) {
            var instructor = instructorRepo.findById(req.instructorId())
                    .orElseThrow(() -> new RuntimeException("Instructor %d not found".formatted(req.instructorId())));
            entity.setInstructor(instructor);
        }
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