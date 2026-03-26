package edu.unimagdalena.lms.services;

import edu.unimagdalena.lms.api.dto.LessonDtos.*;
import edu.unimagdalena.lms.entities.Course;
import edu.unimagdalena.lms.entities.Lesson;
import edu.unimagdalena.lms.repositories.LessonRepository;
import edu.unimagdalena.lms.services.mapper.LessonMapper;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class LessonServiceImpl implements LessonService {

    private final LessonRepository repo;
    private final LessonMapper mapper;

    @Override
    public LessonResponse create(LessonCreateRequest req) {
        Lesson lesson = mapper.toEntity(req);
        lesson.setCourse(Course.builder().id(req.courseId()).build());

        return mapper.toResponse(repo.save(lesson));
    }

    @Override
    @Transactional(readOnly = true)
    public LessonResponse get(Long id) {
        return repo.findById(id)
                .map(mapper::toResponse)
                .orElseThrow(() -> new RuntimeException("Lesson " + id + " no existe"));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<LessonResponse> list(Pageable pageable) {
        return repo.findAll(pageable)
                .map(mapper::toResponse);
    }

    @Override
    public LessonResponse update(Long id, LessonUpdateRequest req) {
        var lesson = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Lesson " + id + " no existe"));

        mapper.patch(req, lesson);

        return mapper.toResponse(lesson);
    }

    @Override
    public void delete(Long id) {
        repo.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<LessonResponse> findByTitle(String title) {
        return repo.findByTitleIgnoreCase(title)
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<LessonResponse> findByCourseTitle(String title) {
        return repo.findByCourseTitleIgnoreCase(title)
                .stream()
                .map(mapper::toResponse)
                .toList();
    }
}