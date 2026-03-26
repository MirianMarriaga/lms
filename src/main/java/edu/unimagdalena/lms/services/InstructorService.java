package edu.unimagdalena.lms.services;

import edu.unimagdalena.lms.api.dto.InstructorDtos.*;

import java.util.List;

public interface InstructorService {
    InstructorResponse create(InstructorResponse req);
    InstructorResponse get(Long id);
    InstructorResponse update(long id, InstructorUpdateRequest req);
    List<InstructorResponse> list();
    List<InstructorResponse> getByEmail (String email);
    void delete(long id);
}
