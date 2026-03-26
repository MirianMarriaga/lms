package edu.unimagdalena.lms.services;

import edu.unimagdalena.lms.api.dto.StudentDtos.*;
import edu.unimagdalena.lms.entities.Student;
import edu.unimagdalena.lms.repositories.StudentRepository;
import edu.unimagdalena.lms.services.mapper.IStudentMapper;
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
class StudentServiceImplTest {

    @Mock StudentRepository repo;
    @Spy IStudentMapper mapper = Mappers.getMapper(IStudentMapper.class);
    @InjectMocks StudentServiceImpl service;

    @Test
    void shouldCreateAndReturnResponseDto() {
        var req = new StudentCreateRequest("rmramirez@gmail.com", "rosa margarita");
        when(repo.save(any())).thenAnswer(inv -> {
            Student s = inv.getArgument(0);
            s.setId(1L);
            return s;
        });
        var res = service.create(req);
        assertThat(res.id()).isEqualTo(1L);
        assertThat(res.email()).isEqualTo("rmramirez@gmail.com");
        verify(repo).save(any(Student.class));
    }

    @Test
    void shouldUpdateViaPatch() {
        var entity = Student.builder()
                .id(1L)
                .email("rmramirez@gmail.com")
                .fullName("rosa margarita")
                .createdAt(Instant.now())
                .updatedAt(Instant.now())
                .build();
        when(repo.findById(1L)).thenReturn(Optional.of(entity));
        when(repo.save(any())).thenReturn(entity);

        var req = new StudentUpdateRequest(null, "rosa ramirez");
        var result = service.update(1L, req);

        assertThat(result.fullName()).isEqualTo("rosa ramirez");
        assertThat(result.email()).isEqualTo("rmramirez@gmail.com");
    }

    @Test
    void shouldListAllStudents() {
        var entity = Student.builder()
                .email("rmramirez@gmail.com")
                .fullName("rosa margarita")
                .createdAt(Instant.now())
                .updatedAt(Instant.now())
                .build();
        when(repo.findAll()).thenReturn(List.of(entity));
        var result = service.list();
        assertThat(result).hasSize(1);
        assertThat(result.get(0).email()).isEqualTo("rmramirez@gmail.com");
    }
}