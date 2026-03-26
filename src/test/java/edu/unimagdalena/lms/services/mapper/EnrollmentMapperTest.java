package edu.unimagdalena.lms.services.mapper;

import edu.unimagdalena.lms.api.dto.EnrollmentDtos.*;
import edu.unimagdalena.lms.entities.*;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;

class EnrollmentMapperTest {

    private final EnrollmentMapper mapper = Mappers.getMapper(EnrollmentMapper.class);

    @Test
    void toEntity_shouldIgnoreRelations() {
        var entity = mapper.toEntity(new EnrollmentCreateRequest(1L, 2L, "ACTIVO"));

        assertThat(entity.getStudent()).isNull();
        assertThat(entity.getCourse()).isNull();
        assertThat(entity.getEnrolledAt()).isNull();
        assertThat(entity.getStatus()).isEqualTo("ACTIVO");
    }

    @Test
    void toResponse_shouldMapIds() {
        var e = Enrollment.builder()
                .id(1L)
                .status("ACTIVO")
                .enrolledAt(Instant.now())
                .student(Student.builder().id(5L).build())
                .course(Course.builder().id(9L).build())
                .build();

        var dto = mapper.toResponse(e);

        assertThat(dto.studentId()).isEqualTo(5L);
        assertThat(dto.courseId()).isEqualTo(9L);
    }

    @Test
    void patch_shouldUpdateStatus() {
        var e = Enrollment.builder().status("PENDIENTE").build();

        mapper.patch(new EnrollmentUpdateRequest("FINALIZADO"), e);

        assertThat(e.getStatus()).isEqualTo("FINALIZADO");
    }
}