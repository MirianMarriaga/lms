package edu.unimagdalena.lms.services.mapper;

import edu.unimagdalena.lms.api.dto.AssessmentDtos.*;
import edu.unimagdalena.lms.entities.Assessment;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface AssessmentMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "student", ignore = true)
    @Mapping(target = "course", ignore = true)
    @Mapping(target = "takenAt", ignore = true)
    Assessment toEntity(AssessmentCreateRequest req);

    @Mapping(target = "studentId", source = "student.id")
    @Mapping(target = "courseId", source = "course.id")
    AssessmentResponse toResponse(Assessment entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void patch(AssessmentUpdateRequest req, @MappingTarget Assessment entity);
}