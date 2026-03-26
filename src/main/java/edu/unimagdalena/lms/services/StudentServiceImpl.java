package edu.unimagdalena.lms.services;

import edu.unimagdalena.lms.api.dto.StudentDtos.*;
import edu.unimagdalena.lms.repositories.StudentRepository;
import edu.unimagdalena.lms.services.mapper.IStudentMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class StudentServiceImpl implements StudentService {

    private final StudentRepository repo;
    private final IStudentMapper mapper;

    @Transactional
    @Override
    public StudentResponse create(StudentCreateRequest req) {
        var entity = mapper.toEntity(req);
        entity.setCreatedAt(Instant.now());
        entity.setUpdatedAt(Instant.now());
        var saved = repo.save(entity);
        return mapper.toResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public StudentResponse get(Long id) {
        return repo.findById(id)
                .map(s -> mapper.toResponse(s))
                .orElseThrow(() -> new RuntimeException("Student %d not found".formatted(id)));
    }

    @Override
    @Transactional(readOnly = true)
    public List<StudentResponse> list() {
        return repo.findAll().stream()
                .map(s -> mapper.toResponse(s))
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<StudentResponse> getByEmail(String email) {
        return repo.findByEmailIgnoreCase(email)
                .stream()
                .map(s -> mapper.toResponse(s))
                .toList();
    }

    @Override
    @Transactional
    public StudentResponse update(Long id, StudentUpdateRequest req) {
        var entity = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Student %d not found".formatted(id)));
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