package edu.unimagdalena.lms.services.mapper;

import edu.unimagdalena.lms.api.dto.CourseDtos.*;
import edu.unimagdalena.lms.entities.Course;
import edu.unimagdalena.lms.entities.Instructor;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;

public class CourseMapperTest {
    private final ICourseMapper mapper = Mappers.getMapper(ICourseMapper.class);

    @Test
    void toEntity_shouldMapCreateRequest() {
        var req = new CourseCreateRequest(10L, "java avanzado", "publicado", true);

        Course c = mapper.toEntity(req);

        assertThat(c.getTitle()).isEqualTo("java avanzado");
        assertThat(c.getStatus()).isEqualTo("publicado");
        assertThat(c.isActive()).isTrue();
    }

    @Test
    void toResponse_shouldMapEntity() {
        Instructor instructor = Instructor.builder()
                .email("jrsalazar@gmail.com")
                .fullName("jorge salazar")
                .createdAt(Instant.now())
                .updatedAt(Instant.now()).build();

        Course course = Course.builder()
                .title("java avanzado")
                .status("publicado")
                .active(true)
                .instructor(instructor)
                .createdAt(Instant.now())
                .updatedAt(Instant.now())
                .build();

        CourseResponse result = mapper.toResponse(course);

        assertThat(result.title()).isEqualTo("java avanzado");
        assertThat(result.instructorName()).isEqualTo("jorge salazar");
        assertThat(result.active()).isTrue();
    }


    @Test
    void patch_shouldIgnoreNulls() {
        Course course = Course.builder()
                .title("java avanzado")
                .status("publicado")
                .active(true)
                .createdAt(Instant.now())
                .updatedAt(Instant.now())
                .build();

        var changes = new CourseUpdateRequest(null, null, "borrador", false);

        mapper.patch(course, changes);

        assertThat(course.getTitle()).isEqualTo("java avanzado");
        assertThat(course.getStatus()).isEqualTo("borrador");
        assertThat(course.isActive()).isFalse();
    }
}
