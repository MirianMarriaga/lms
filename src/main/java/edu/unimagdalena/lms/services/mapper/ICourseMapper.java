package edu.unimagdalena.lms.services.mapper;

import edu.unimagdalena.lms.api.dto.CourseDtos.*;
import edu.unimagdalena.lms.entities.Course;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface ICourseMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "instructor", ignore = true)
    Course toEntity(CourseCreateRequest req);

    @Mapping(target = "instructorName", source = "instructor.fullName")
    CourseResponse toResponse(Course entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void patch(@MappingTarget Course target, CourseUpdateRequest changes);



}
