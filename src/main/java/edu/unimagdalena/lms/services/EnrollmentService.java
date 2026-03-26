package edu.unimagdalena.lms.services;
import edu.unimagdalena.lms.api.dto.EnrollmentDtos.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface EnrollmentService {

    EnrollmentResponse create(EnrollmentCreateRequest request);

    EnrollmentResponse get(Long id);

    Page<EnrollmentResponse> list(Pageable pageable);

    EnrollmentResponse update(Long id, EnrollmentUpdateRequest request);

    void delete(Long id);

    List<EnrollmentResponse> findByCourseTitle(String title);
}