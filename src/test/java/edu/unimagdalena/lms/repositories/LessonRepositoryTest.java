package edu.unimagdalena.lms.repositories;

import edu.unimagdalena.lms.entities.Lesson;
import edu.unimagdalena.lms.entities.Course;
import edu.unimagdalena.lms.entities.Instructor;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

public class LessonRepositoryTest extends AbstractRepositoryIT {

    @Autowired
    InstructorRepository instructorRepo;
    @Autowired
    CourseRepository courseRepo;
    @Autowired
    LessonRepository lessonRepo;

    @Test
    @DisplayName("Lesson: Encontrar lecciones por su titulo")
    void shouldFindByTitleIgnoreCase(){
        //Given
        var instructor = instructorRepo.save(Instructor.builder().email("joleramirez@gmail.com")
                .fullName("José Luis Ramírez").createdAt(Instant.now()).updatedAt(Instant.now())
                .build());

        var curso = courseRepo.save(Course.builder().instructor(instructor)
                .title("Introducción a la Programación en Java")
                .status("Publicado").active(true).createdAt(Instant.now()).updatedAt(Instant.now()).build());

        lessonRepo.save(Lesson.builder().course(curso).title("¿Qué es la programación?").orderIndex(1).build());
        lessonRepo.save(Lesson.builder().course(curso).title("Variables y tipos de datos en Java").orderIndex(2).build());

        //When
        List<Lesson> resultado = lessonRepo.findByTitleIgnoreCase("Variables y tipos de datos en Java");

        //Then
        assertThat(resultado).isNotEmpty();
        assertThat(resultado).hasSize(1);
        assertThat(resultado).extracting(Lesson::getTitle).containsExactly("Variables y tipos de datos en Java");
    }

    @Test
    @DisplayName("Lesson: Encontrar lecciones por su curso")
    void shouldFindByCourseTitleIgnoreCase(){
        //Given
        var instructor = instructorRepo.save(Instructor.builder().email("joleramirez@gmail.com")
                .fullName("José Luis Ramírez").createdAt(Instant.now()).updatedAt(Instant.now())
                .build());

        var curso1 = courseRepo.save(Course.builder().instructor(instructor)
                .title("Introducción a la Programación en Java")
                .status("Publicado").active(true).createdAt(Instant.now()).updatedAt(Instant.now()).build());
        var curso2 = courseRepo.save(Course.builder().instructor(instructor)
                .title("Introducción a bases de datos")
                .status("Borrador").active(false).createdAt(Instant.now()).updatedAt(Instant.now()).build());

            //lecciones curso 1
        lessonRepo.save(Lesson.builder().course(curso1).title("Variables y tipos de datos en Java").orderIndex(1).build());

            //lecciones curso 2
        lessonRepo.save(Lesson.builder().course(curso2).title("Tablas y registros").orderIndex(1).build());
        lessonRepo.save(Lesson.builder().course(curso2).title("Tablas y registros").orderIndex(2).build());

        //When
        List<Lesson> resultado = lessonRepo.findByCourseTitleIgnoreCase("INTRODUCCIÓN A BASES DE DATOS");

        //Then
        assertThat(resultado).isNotEmpty();
        assertThat(resultado).hasSize(2);
        assertThat(resultado).extracting(l -> l.getCourse().getTitle()).containsOnly(curso2.getTitle()); //dos lecciones de Introducción a bases de datos
    }
}
