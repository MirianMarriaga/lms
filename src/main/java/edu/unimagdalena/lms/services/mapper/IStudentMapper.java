package edu.unimagdalena.lms.services.mapper;

import edu.unimagdalena.lms.api.dto.StudentDtos.*;
import edu.unimagdalena.lms.entities.Student;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface IStudentMapper {
    Student toEntity(StudentCreateRequest req);

    StudentResponse toResponse(Student s);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void patch(@MappingTarget Student target, StudentUpdateRequest dto);



}
