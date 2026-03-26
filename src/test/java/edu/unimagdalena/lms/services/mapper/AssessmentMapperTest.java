package edu.unimagdalena.lms.services.mapper;

import edu.unimagdalena.lms.api.dto.AssessmentDtos.*;
import edu.unimagdalena.lms.entities.*;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;

class AssessmentMapperTest {

    private final AssessmentMapper mapper = Mappers.getMapper(AssessmentMapper.class);

    @Test
    void toEntity_shouldIgnoreRelations() {
        var entity = mapper.toEntity(
                new AssessmentCreateRequest("Parcial", 5, 1L, 2L)
        );

        assertThat(entity.getStudent()).isNull();
        assertThat(entity.getCourse()).isNull();
        assertThat(entity.getTakenAt()).isNull();
        assertThat(entity.getType()).isEqualTo("Parcial");
        assertThat(entity.getScore()).isEqualTo(5);
    }

    @Test
    void toResponse_shouldMapIds() {
        var a = Assessment.builder()
                .id(10L)
                .type("Quiz")
                .score(4)
                .takenAt(Instant.now())
                .student(Student.builder().id(1L).build())
                .course(Course.builder().id(2L).build())
                .build();

        var dto = mapper.toResponse(a);

        assertThat(dto.id()).isEqualTo(10L);
        assertThat(dto.studentId()).isEqualTo(1L);
        assertThat(dto.courseId()).isEqualTo(2L);
    }

    @Test
    void patch_shouldUpdateOnlyNonNull() {
        var entity = Assessment.builder()
                .type("Evaluación vieja")
                .score(2)
                .build();

        mapper.patch(
                new AssessmentUpdateRequest("Evaluación nueva", null),
                entity
        );

        assertThat(entity.getType()).isEqualTo("Evaluación nueva");
        assertThat(entity.getScore()).isEqualTo(2);
    }
}