package edu.unimagdalena.lms.services;

import edu.unimagdalena.lms.api.dto.StudentDtos.*;

import java.util.List;

public interface StudentService {
    StudentResponse create(StudentCreateRequest req);
    StudentResponse get(Long id);
    StudentResponse update(Long id, StudentUpdateRequest req);
    List<StudentResponse> list();
    List <StudentResponse> getByEmail (String email);
    void delete(Long id);

}
