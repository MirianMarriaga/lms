package edu.unimagdalena.lms.services.mapper;

import edu.unimagdalena.lms.api.dto.StudentDtos.*;
import edu.unimagdalena.lms.entities.Student;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.assertj.core.api.Assertions.assertThat;

class StudentMapperTest {
    private final IStudentMapper mapper = Mappers.getMapper(IStudentMapper.class);

    @Test
    void toEntity_shouldMapCreate(){
        Student s = mapper.toEntity(new StudentCreateRequest("rmramirez@gmail.com", "rosa margarita"));
        assertThat(s.getFullName()).isEqualTo("rosa margarita");
    }

    @Test
    void toResponse_shouldMapEntity() {
        var s = Student.builder().id(500).fullName("rosa margarita").build();
        StudentResponse dto = mapper.toResponse(s);
        assertThat(dto.id()).isEqualTo(500);
    }

    @Test
    void patch_shouldIgnoreNulls() {
        var s = Student.builder().id(500).fullName("rosa margarita").build();
        mapper.patch(s, new StudentUpdateRequest(null,"rosa castro"));
        assertThat(s.getFullName()).isEqualTo("rosa castro");
    }
}
