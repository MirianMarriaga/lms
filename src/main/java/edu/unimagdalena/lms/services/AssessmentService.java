package edu.unimagdalena.lms.services;
import edu.unimagdalena.lms.api.dto.AssessmentDtos.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AssessmentService {

    AssessmentResponse create(AssessmentCreateRequest request);

    AssessmentResponse get(Long id);

    Page<AssessmentResponse> list(Pageable pageable);

    AssessmentResponse update(Long id, AssessmentUpdateRequest request);

    void delete(Long id);

    List<AssessmentResponse> findByCourseTitle(String title);
}