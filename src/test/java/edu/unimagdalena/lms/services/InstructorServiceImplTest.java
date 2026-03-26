package edu.unimagdalena.lms.services;

import edu.unimagdalena.lms.api.dto.InstructorDtos.*;
import edu.unimagdalena.lms.entities.Instructor;
import edu.unimagdalena.lms.entities.InstructorProfile;
import edu.unimagdalena.lms.repositories.InstructorRepository;
import edu.unimagdalena.lms.services.mapper.IInstructorMapper;
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
class InstructorServiceImplTest {

    @Mock InstructorRepository repo;
    @InjectMocks InstructorServiceImpl service;
    @Spy private IInstructorMapper mapper = Mappers.getMapper(IInstructorMapper.class);

    @Test
    void shouldCreateAndReturnResponseDto() {
        var req = new InstructorCreateRequest("jrsalazar@gmail.com", "jorge salazar",
                new InstructorProfileDto("+57 310 456 7890",
                        "ejemplo de bio."));
        when(repo.save(any())).thenAnswer(inv -> {
            Instructor i = inv.getArgument(0);
            i.setId(1L);
            return i;
        });
        var res = service.create(req);
        assertThat(res.id()).isEqualTo(1L);
        assertThat(res.email()).isEqualTo("jrsalazar@gmail.com");
        verify(repo).save(any(Instructor.class));
    }

    @Test
    void shouldUpdateViaPatch() {
        var entity = Instructor.builder()
                .id(7L)
                .email("jrsalazar@gmail.com")
                .fullName("jorge salazar")
                .createdAt(Instant.now())
                .updatedAt(Instant.now())
                .instructorProfile(InstructorProfile.builder()
                        .phone("+57 310 456 7890")
                        .bio("bio original")
                        .build())
                .build();

        when(repo.findById(7L)).thenReturn(Optional.of(entity));
        when(repo.save(any())).thenReturn(entity);

        var req = new InstructorUpdateRequest(null, "jorge ricardo",
                new InstructorProfileDto(null, "especialista en metodologias de software."));
        var result = service.update(7L, req);
        assertThat(result.fullName()).isEqualTo("jorge ricardo");
        assertThat(result.profile().bio()).isEqualTo("especialista en metodologias de software.");
    }

    @Test
    void shouldListAllInstructors() {
        var entity = Instructor.builder()
                .email("jrsalazar@gmail.com")
                .fullName("jorge salazar")
                .createdAt(Instant.now())
                .updatedAt(Instant.now())
                .build();
        when(repo.findAll()).thenReturn(List.of(entity));
        var result = service.list();
        assertThat(result).hasSize(1);
        assertThat(result.get(0).email()).isEqualTo("jrsalazar@gmail.com");
    }
}