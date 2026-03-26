package edu.unimagdalena.lms.services;

import edu.unimagdalena.lms.api.dto.AssessmentDtos.*;
import edu.unimagdalena.lms.entities.Assessment;
import edu.unimagdalena.lms.entities.Course;
import edu.unimagdalena.lms.entities.Student;
import edu.unimagdalena.lms.repositories.AssessmentRepository;
import edu.unimagdalena.lms.services.mapper.AssessmentMapper;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class AssessmentServiceImpl implements AssessmentService {

    private final AssessmentRepository repo;
    private final AssessmentMapper mapper;

    @Override
    public AssessmentResponse create(AssessmentCreateRequest req) {
        Assessment a = mapper.toEntity(req);

        a.setStudent(Student.builder().id(req.studentId()).build());
        a.setCourse(Course.builder().id(req.courseId()).build());
        a.setTakenAt(Instant.now());

        return mapper.toResponse(repo.save(a));
    }

    @Override
    @Transactional(readOnly = true)
    public AssessmentResponse get(Long id) {
        return repo.findById(id)
                .map(mapper::toResponse)
                .orElseThrow(() -> new RuntimeException("Assessment " + id + " no existe"));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AssessmentResponse> list(Pageable pageable) {
        return repo.findAll(pageable)
                .map(mapper::toResponse);
    }

    @Override
    public AssessmentResponse update(Long id, AssessmentUpdateRequest req) {
        var a = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Assessment " + id + " no existe"));

        mapper.patch(req, a);

        return mapper.toResponse(a);
    }

    @Override
    public void delete(Long id) {
        repo.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AssessmentResponse> findByCourseTitle(String title) {
        return repo.findByCourseTitleIgnoreCase(title)
                .stream()
                .map(mapper::toResponse)
                .toList();
    }
}