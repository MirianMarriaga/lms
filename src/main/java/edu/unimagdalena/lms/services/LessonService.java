package edu.unimagdalena.lms.services;
import edu.unimagdalena.lms.api.dto.LessonDtos.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface LessonService {

    LessonResponse create(LessonCreateRequest request);

    LessonResponse get(Long id);

    Page<LessonResponse> list(Pageable pageable);

    LessonResponse update(Long id, LessonUpdateRequest request);

    void delete(Long id);

    List<LessonResponse> findByTitle(String title);

    List<LessonResponse> findByCourseTitle(String title);
}