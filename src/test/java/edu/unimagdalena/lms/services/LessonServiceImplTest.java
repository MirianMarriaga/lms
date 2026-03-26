package edu.unimagdalena.lms.services;

import edu.unimagdalena.lms.api.dto.LessonDtos.*;
import edu.unimagdalena.lms.entities.*;
import edu.unimagdalena.lms.repositories.LessonRepository;
import edu.unimagdalena.lms.services.mapper.LessonMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LessonServiceImplTest {

    @Mock
    private LessonRepository lessonRepo;

    @Mock
    private LessonMapper lessonMapper;

    @InjectMocks
    private LessonServiceImpl lessonService;

    @Test
    void shouldCreateLessonAndMapToDto() {
        var createRequest = new LessonCreateRequest("Introducción", 1, 2L);
        var lessonEntity = new Lesson();
        var savedLesson = Lesson.builder()
                .id(1L)
                .title("Introducción")
                .orderIndex(1)
                .course(Course.builder().id(2L).build())
                .build();
        var lessonResponse = new LessonResponse(1L, "Introducción", 1, 2L);

        when(lessonMapper.toEntity(createRequest)).thenReturn(lessonEntity);
        when(lessonRepo.save(any())).thenAnswer(inv -> {
            Lesson l = inv.getArgument(0);
            l.setId(1L);
            return savedLesson;
        });
        when(lessonMapper.toResponse(savedLesson)).thenReturn(lessonResponse);

        var result = lessonService.create(createRequest);

        assertThat(result.id()).isEqualTo(1L);
        assertThat(result.title()).isEqualTo("Introducción");
        assertThat(result.orderIndex()).isEqualTo(1);
        assertThat(result.courseId()).isEqualTo(2L);
    }

    @Test
    void shouldGetLessonAndMapToDto() {
        var lessonEntity = new Lesson();
        var lessonResponse = new LessonResponse(1L, "Clase", 1, 2L);

        when(lessonRepo.findById(1L)).thenReturn(Optional.of(lessonEntity));
        when(lessonMapper.toResponse(lessonEntity)).thenReturn(lessonResponse);

        var result = lessonService.get(1L);

        assertThat(result.id()).isEqualTo(1L);
        assertThat(result.title()).isEqualTo("Clase");
        assertThat(result.orderIndex()).isEqualTo(1);
        assertThat(result.courseId()).isEqualTo(2L);
    }

    @Test
    void shouldUpdateLessonAndMapToDto() {
        var lessonEntity = new Lesson();
        var updateRequest = new LessonUpdateRequest("Nueva clase", 2, null);
        var lessonResponse = new LessonResponse(1L, "Nueva clase", 2, 2L);

        when(lessonRepo.findById(1L)).thenReturn(Optional.of(lessonEntity));
        when(lessonMapper.toResponse(lessonEntity)).thenReturn(lessonResponse);

        var result = lessonService.update(1L, updateRequest);

        verify(lessonMapper).patch(updateRequest, lessonEntity);
        assertThat(result.title()).isEqualTo("Nueva clase");
        assertThat(result.orderIndex()).isEqualTo(2);
        assertThat(result.courseId()).isEqualTo(2L);
    }

    @Test
    void shouldDeleteLesson() {
        lessonService.delete(1L);
        verify(lessonRepo).deleteById(1L);
    }
}