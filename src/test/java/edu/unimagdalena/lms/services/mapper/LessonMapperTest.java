package edu.unimagdalena.lms.services.mapper;

import edu.unimagdalena.lms.api.dto.LessonDtos.*;
import edu.unimagdalena.lms.entities.*;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.assertj.core.api.Assertions.assertThat;

class LessonMapperTest {

    private final LessonMapper mapper = Mappers.getMapper(LessonMapper.class);

    @Test
    void toEntity_shouldIgnoreCourse() {
        var entity = mapper.toEntity(
                new LessonCreateRequest("Introducción", 1, 1L)
        );

        assertThat(entity.getCourse()).isNull();
        assertThat(entity.getTitle()).isEqualTo("Introducción");
        assertThat(entity.getOrderIndex()).isEqualTo(1);
    }

    @Test
    void toResponse_shouldMapCourseId() {
        var lesson = Lesson.builder()
                .id(1L)
                .title("Clase 1")
                .orderIndex(1)
                .course(Course.builder().id(7L).build())
                .build();

        var dto = mapper.toResponse(lesson);

        assertThat(dto.courseId()).isEqualTo(7L);
    }

    @Test
    void patch_shouldIgnoreNulls() {
        var lesson = Lesson.builder()
                .title("Clase antigua")
                .orderIndex(1)
                .build();

        mapper.patch(
                new LessonUpdateRequest("Clase nueva", null, null),
                lesson
        );

        assertThat(lesson.getTitle()).isEqualTo("Clase nueva");
        assertThat(lesson.getOrderIndex()).isEqualTo(1);
    }
}