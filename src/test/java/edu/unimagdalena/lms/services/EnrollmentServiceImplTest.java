package edu.unimagdalena.lms.services;

import edu.unimagdalena.lms.api.dto.EnrollmentDtos.*;
import edu.unimagdalena.lms.entities.*;
import edu.unimagdalena.lms.repositories.EnrollmentRepository;
import edu.unimagdalena.lms.services.mapper.EnrollmentMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;

import java.time.Instant;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(org.mockito.junit.jupiter.MockitoExtension.class)
class EnrollmentServiceImplTest {

    @Mock
    private EnrollmentRepository enrollmentRepo;

    @Mock
    private EnrollmentMapper enrollmentMapper;

    @InjectMocks
    private EnrollmentServiceImpl enrollmentService;

    @Test
    void shouldCreateEnrollmentAndMapToDto() {

        var createRequest = new EnrollmentCreateRequest(1L, 2L, "ACTIVO");

        var enrollmentEntity = new Enrollment();

        var savedEnrollment = Enrollment.builder()
                .id(1L)
                .status("ACTIVO")
                .enrolledAt(Instant.now())
                .build();

        var enrollmentResponse = new EnrollmentResponse(
                1L,
                1L,
                2L,
                "ACTIVO",
                savedEnrollment.getEnrolledAt()
        );


        when(enrollmentMapper.toEntity(createRequest)).thenReturn(enrollmentEntity);
        when(enrollmentRepo.save(any())).thenAnswer(inv -> {
            Enrollment e = inv.getArgument(0);
            e.setId(1L);
            return savedEnrollment;
        });
        when(enrollmentMapper.toResponse(savedEnrollment)).thenReturn(enrollmentResponse);

        var result = enrollmentService.create(createRequest);

        assertThat(result.id()).isEqualTo(1L);
        assertThat(result.status()).isEqualTo("ACTIVO");
    }

    @Test
    void shouldGetEnrollmentAndMapToDto() {
        var enrollmentEntity = new Enrollment();
        var enrollmentResponse = new EnrollmentResponse(
                1L, 1L, 2L, "ACTIVO", Instant.now()
        );

        when(enrollmentRepo.findById(1L)).thenReturn(Optional.of(enrollmentEntity));
        when(enrollmentMapper.toResponse(enrollmentEntity)).thenReturn(enrollmentResponse);

        var result = enrollmentService.get(1L);

        assertThat(result.id()).isEqualTo(1L);
        assertThat(result.status()).isEqualTo("ACTIVO");
    }

    @Test
    void shouldUpdateEnrollmentAndMapToDto() {
        var enrollmentEntity = new Enrollment();
        var updateRequest = new EnrollmentUpdateRequest("FINALIZADO");
        var enrollmentResponse = new EnrollmentResponse(
                1L, 1L, 2L, "FINALIZADO", Instant.now()
        );

        when(enrollmentRepo.findById(1L)).thenReturn(Optional.of(enrollmentEntity));
        when(enrollmentMapper.toResponse(enrollmentEntity)).thenReturn(enrollmentResponse);

        var result = enrollmentService.update(1L, updateRequest);

        verify(enrollmentMapper).patch(updateRequest, enrollmentEntity);
        assertThat(result.status()).isEqualTo("FINALIZADO");
    }

    @Test
    void shouldDeleteEnrollment() {
        enrollmentService.delete(1L);
        verify(enrollmentRepo).deleteById(1L);
    }
}