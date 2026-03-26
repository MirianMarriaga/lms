package edu.unimagdalena.lms.services.mapper;
import edu.unimagdalena.lms.api.dto.LessonDtos.*;
import edu.unimagdalena.lms.entities.Lesson;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface LessonMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "course", ignore = true)
    Lesson toEntity(LessonCreateRequest req);

    @Mapping(target = "courseId", source = "course.id")
    LessonResponse toResponse(Lesson entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "course", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void patch(LessonUpdateRequest req, @MappingTarget Lesson entity);
}