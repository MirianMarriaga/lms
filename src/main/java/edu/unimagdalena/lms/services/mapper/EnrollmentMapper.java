package edu.unimagdalena.lms.services.mapper;

import edu.unimagdalena.lms.api.dto.EnrollmentDtos.*;
import edu.unimagdalena.lms.entities.Enrollment;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface EnrollmentMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "student", ignore = true)
    @Mapping(target = "course", ignore = true)
    @Mapping(target = "enrolledAt", ignore = true)
    Enrollment toEntity(EnrollmentCreateRequest req);

    @Mapping(target = "studentId", source = "student.id")
    @Mapping(target = "courseId", source = "course.id")
    EnrollmentResponse toResponse(Enrollment entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void patch(EnrollmentUpdateRequest req, @MappingTarget Enrollment entity);
}