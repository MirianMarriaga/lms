package edu.unimagdalena.lms.services;

import edu.unimagdalena.lms.api.dto.AssessmentDtos.*;
import edu.unimagdalena.lms.entities.*;
import edu.unimagdalena.lms.repositories.AssessmentRepository;
import edu.unimagdalena.lms.services.mapper.AssessmentMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AssessmentServiceImplTest {

    @Mock
    private AssessmentRepository assessmentRepo;

    @Mock
    private AssessmentMapper assessmentMapper;

    @InjectMocks
    private AssessmentServiceImpl assessmentService;

    @Test
    void shouldCreateAssessmentAndMapToDto() {

        var createRequest = new AssessmentCreateRequest("Parcial", 5, 1L, 2L);

        var assessmentEntity = new Assessment();

        var savedAssessment = Assessment.builder()
                .id(10L)
                .type("Parcial")
                .score(5)
                .takenAt(Instant.now())
                .student(Student.builder().id(1L).build())
                .course(Course.builder().id(2L).build())
                .build();


        var assessmentResponse = new AssessmentResponse(
                10L,
                "Parcial",
                5,
                1L,
                2L,
                savedAssessment.getTakenAt()
        );


        when(assessmentMapper.toEntity(createRequest)).thenReturn(assessmentEntity);
        when(assessmentRepo.save(any())).thenAnswer(inv -> {
            Assessment a = inv.getArgument(0);
            a.setId(10L);
            return savedAssessment;
        });
        when(assessmentMapper.toResponse(savedAssessment)).thenReturn(assessmentResponse);


        var result = assessmentService.create(createRequest);

        assertThat(result.id()).isEqualTo(10L);
        assertThat(result.type()).isEqualTo("Parcial");
        assertThat(result.score()).isEqualTo(5);
        verify(assessmentRepo).save(any());
    }

    @Test
    void shouldGetAssessmentAndMapToDto() {
        var assessmentEntity = new Assessment();
        var assessmentResponse = new AssessmentResponse(
                1L, "Quiz", 4, 1L, 2L, Instant.now()
        );

        when(assessmentRepo.findById(1L)).thenReturn(Optional.of(assessmentEntity));
        when(assessmentMapper.toResponse(assessmentEntity)).thenReturn(assessmentResponse);

        var result = assessmentService.get(1L);

        assertThat(result.id()).isEqualTo(1L);
        assertThat(result.type()).isEqualTo("Quiz");
        assertThat(result.score()).isEqualTo(4);
    }

    @Test
    void shouldUpdateAssessmentAndMapToDto() {
        var assessmentEntity = new Assessment();
        var updateRequest = new AssessmentUpdateRequest("Final", 10);
        var assessmentResponse = new AssessmentResponse(
                1L, "Final", 10, 1L, 2L, Instant.now()
        );

        when(assessmentRepo.findById(1L)).thenReturn(Optional.of(assessmentEntity));
        when(assessmentMapper.toResponse(assessmentEntity)).thenReturn(assessmentResponse);

        var result = assessmentService.update(1L, updateRequest);

        verify(assessmentMapper).patch(updateRequest, assessmentEntity);
        assertThat(result.type()).isEqualTo("Final");
        assertThat(result.score()).isEqualTo(10);
    }

    @Test
    void shouldDeleteAssessment() {
        assessmentService.delete(1L);
        verify(assessmentRepo).deleteById(1L);
    }

    @Test
    void shouldListAssessmentsAndMapToDto() {
        var page = new PageImpl<>(List.of(new Assessment()));
        when(assessmentRepo.findAll(any(Pageable.class))).thenReturn(page);
        when(assessmentMapper.toResponse(any())).thenReturn(
                new AssessmentResponse(1L, "Quiz", 5, 1L, 2L, Instant.now())
        );

        var result = assessmentService.list(PageRequest.of(0, 10));

        assertThat(result.getContent()).hasSize(1);
        assertThat(result.getContent().get(0).type()).isEqualTo("Quiz");
        assertThat(result.getContent().get(0).score()).isEqualTo(5);
    }
}