package edu.unimagdalena.lms.services;

import edu.unimagdalena.lms.api.dto.EnrollmentDtos.*;
import edu.unimagdalena.lms.entities.Course;
import edu.unimagdalena.lms.entities.Enrollment;
import edu.unimagdalena.lms.entities.Student;
import edu.unimagdalena.lms.repositories.EnrollmentRepository;
import edu.unimagdalena.lms.services.mapper.EnrollmentMapper;

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
public class EnrollmentServiceImpl implements EnrollmentService {

    private final EnrollmentRepository repo;
    private final EnrollmentMapper mapper;

    @Override
    public EnrollmentResponse create(EnrollmentCreateRequest req) {
        Enrollment e = mapper.toEntity(req);

        e.setStudent(Student.builder().id(req.studentId()).build());
        e.setCourse(Course.builder().id(req.courseId()).build());
        e.setEnrolledAt(Instant.now());

        return mapper.toResponse(repo.save(e));
    }

    @Override
    @Transactional(readOnly = true)
    public EnrollmentResponse get(Long id) {
        return repo.findById(id)
                .map(mapper::toResponse)
                .orElseThrow(() -> new RuntimeException("Enrollment " + id + " no existe"));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<EnrollmentResponse> list(Pageable pageable) {
        return repo.findAll(pageable)
                .map(mapper::toResponse);
    }

    @Override
    public EnrollmentResponse update(Long id, EnrollmentUpdateRequest req) {
        var e = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Enrollment " + id + " no existe"));

        mapper.patch(req, e);

        return mapper.toResponse(e);
    }

    @Override
    public void delete(Long id) {
        repo.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<EnrollmentResponse> findByCourseTitle(String title) {
        return repo.findByCourseTitleIgnoreCase(title)
                .stream()
                .map(mapper::toResponse)
                .toList();
    }
}