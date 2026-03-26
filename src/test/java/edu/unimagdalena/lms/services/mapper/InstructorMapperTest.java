package edu.unimagdalena.lms.services.mapper;

import edu.unimagdalena.lms.api.dto.InstructorDtos.*;
import edu.unimagdalena.lms.entities.Instructor;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;

public class InstructorMapperTest {
    private final IInstructorMapper mapper = Mappers.getMapper(IInstructorMapper.class);

    @Test
    void toEntity_shouldMapCreateRequest() {
        var profile = new InstructorProfileDto("+57 310 456 7890", "biografia de ejemplo");
        var req = new InstructorCreateRequest("jrsalazar@gmail.com", "jorge salazar", profile);

        Instructor entity = mapper.toEntity(req);

        assertThat(entity.getEmail()).isEqualTo("jrsalazar@gmail.com");
        assertThat(entity.getFullName()).isEqualTo("jorge salazar");
    }

    @Test
    void toResponse_shouldMapEntity() {
        Instructor i = Instructor.builder()
                .id(10).email("jrsalazar@gmail.com")
                .fullName("jorge salazar")
                .createdAt(Instant.now())
                .updatedAt(Instant.now()).build();

        InstructorResponse dto = mapper.toResponse(i);

        assertThat(dto.id()).isEqualTo(10);
        assertThat(dto.email()).isEqualTo("jrsalazar@gmail.com");
    }

    @Test
    void patch_shouldIgnoreNullsAndCreateProfileIfNeeded() {
        var entity = Instructor.builder()
                .id(10).email("jrsalazar@gmail.com")
                .fullName("jorge salazar")
                .createdAt(Instant.now())
                .updatedAt(Instant.now()).build();
        var changes = new InstructorUpdateRequest("jssalazar@gmail.com", null, new InstructorProfileDto(null, "biografia de ejemplo 2"));

        mapper.patch(entity, changes);

        assertThat(entity.getFullName()).isEqualTo("jorge salazar");
        assertThat(entity.getEmail()).isEqualTo("jssalazar@gmail.com");
        assertThat(entity.getInstructorProfile()).isNotNull();
        assertThat(entity.getInstructorProfile().getBio()).isEqualTo("biografia de ejemplo 2");
    }
}
