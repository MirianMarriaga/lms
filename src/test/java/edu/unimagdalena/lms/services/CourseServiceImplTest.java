package edu.unimagdalena.lms.services;

import edu.unimagdalena.lms.api.dto.CourseDtos.*;
import edu.unimagdalena.lms.entities.Course;
import edu.unimagdalena.lms.entities.Instructor;
import edu.unimagdalena.lms.repositories.CourseRepository;
import edu.unimagdalena.lms.repositories.InstructorRepository;
import edu.unimagdalena.lms.services.mapper.ICourseMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CourseServiceImplTest {

    @Mock CourseRepository repo;
    @Mock InstructorRepository instructorRepo;
    @InjectMocks CourseServiceImpl service;
    @Spy private ICourseMapper mapper = Mappers.getMapper(ICourseMapper.class);

    @Test
    void shouldCreateAndReturnResponseDto() {
        var instructor = Instructor.builder()
                .id(1L)
                .email("jrsalazar@gmail.com")
                .fullName("jorge salazar")
                .createdAt(Instant.now())
                .updatedAt(Instant.now())
                .build();
        var req = new CourseCreateRequest(1L, "java avanzado", "publicado", true);
        when(instructorRepo.findById(1L)).thenReturn(Optional.of(instructor));
        when(repo.save(any())).thenAnswer(inv -> {
            Course c = inv.getArgument(0);
            c.setId(1L);
            return c;
        });
        var res = service.create(req);
        assertThat(res.title()).isEqualTo("java avanzado");
        assertThat(res.instructorName()).isEqualTo("jorge salazar");
        verify(repo).save(any(Course.class));
    }

    @Test
    void shouldUpdateViaPatch() {
        var instructor = Instructor.builder()
                .id(1L)
                .email("jrsalazar@gmail.com")
                .fullName("jorge salazar")
                .createdAt(Instant.now())
                .updatedAt(Instant.now())
                .build();
        var entity = Course.builder()
                .id(1L)
                .title("java avanzado")
                .status("publicado")
                .active(true)
                .instructor(instructor)
                .createdAt(Instant.now())
                .updatedAt(Instant.now())
                .build();
        when(repo.findById(1L)).thenReturn(Optional.of(entity));
        when(repo.save(any())).thenReturn(entity);
        var req = new CourseUpdateRequest(null, "java avanzado ii", "borrador", false);
        var result = service.update(1L, req);
        assertThat(result.title()).isEqualTo("java avanzado ii");
        assertThat(result.status()).isEqualTo("borrador");
    }

    @Test
    void shouldListAllCourses() {
        var instructor = Instructor.builder()
                .email("jrsalazar@gmail.com")
                .fullName("jorge salazar")
                .createdAt(Instant.now())
                .updatedAt(Instant.now())
                .build();
        var entity = Course.builder()
                .title("java avanzado")
                .status("publicado")
                .active(true)
                .instructor(instructor)
                .createdAt(Instant.now())
                .updatedAt(Instant.now())
                .build();
        when(repo.findAll()).thenReturn(List.of(entity));
        var result = service.list();
        assertThat(result).hasSize(1);
        assertThat(result.get(0).title()).isEqualTo("java avanzado");
    }
}