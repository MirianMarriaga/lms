package edu.unimagdalena.lms.services;

import edu.unimagdalena.lms.api.dto.CourseDtos.*;

import java.util.List;

public interface CourseService {
    CourseResponse create(CourseCreateRequest req);
    CourseResponse get(Long id);
    CourseResponse update(Long id, CourseUpdateRequest req);
    List<CourseResponse> list();
    List<CourseResponse> getByTitle(String title);
    List<CourseResponse> getByStatus(String status);
    List<CourseResponse> getByActive(Boolean active);
    List<CourseResponse> getByInstructorId(Long idInstructor);
    void delete(Long id);
}
