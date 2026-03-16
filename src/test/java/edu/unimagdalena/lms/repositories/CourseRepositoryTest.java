package edu.unimagdalena.lms.repositories;

import edu.unimagdalena.lms.entities.Course;
import edu.unimagdalena.lms.entities.Instructor;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Instant;
import java.util.List;

import static org.assertj.core.api.Assertions.extractProperty;
import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

public class CourseRepositoryTest extends AbstractRepositoryIT{

    @Autowired
    InstructorRepository instructorRepo;

    @Autowired
    CourseRepository courseRepo;

    @Test
    @DisplayName("Course: Buscar cursos por titulo")
    void shouldFindByTitleIgnoreCase(){
        //Given
        var instructor1 = instructorRepo.save(Instructor.builder().email("joleramirez@gmail.com")
                .fullName("José Luis Ramírez").createdAt(Instant.now()).updatedAt(Instant.now())
                .build());

        var curso1 = courseRepo.save(Course.builder().instructor(instructor1)
                .title("Introducción a la Programación en Java")
                .status("Publicado").active(true).createdAt(Instant.now()).updatedAt(Instant.now()).build());

        var curso2 = courseRepo.save(Course.builder().instructor(instructor1)
                .title("Introducción a la Programación en Java")
                .status("Borrador").active(false).createdAt(Instant.now()).updatedAt(Instant.now()).build());

        //When
        List<Course> resultado = courseRepo.findByTitleIgnoreCase("INTRODUCCIÓN A LA PROGRAMACIÓN EN JAVA");

        //Then
        assertThat(resultado).isNotEmpty();
        assertThat(resultado).hasSize(2);
        assertThat(resultado).extracting(Course::getTitle).containsExactly("Introducción a la Programación en Java",
                "Introducción a la Programación en Java");
    }

    @Test
    @DisplayName("Course: Buscar cursos por status")
    void shouldFindByStatus(){
        //Given
        var instructor1 = instructorRepo.save(Instructor.builder().email("joleramirez@gmail.com")
                .fullName("José Luis Ramírez").createdAt(Instant.now()).updatedAt(Instant.now())
                .build());

        var curso1 = courseRepo.save(Course.builder().instructor(instructor1)
                .title("Introducción a la Programación en Java")
                .status("Publicado").active(true).createdAt(Instant.now()).updatedAt(Instant.now()).build());

        var curso2 = courseRepo.save(Course.builder().instructor(instructor1)
                .title("Introducción a la Programación en Java")
                .status("Borrador").active(false).createdAt(Instant.now()).updatedAt(Instant.now()).build());

        //When

        List<Course> resultado = courseRepo.findByStatus("Publicado");

        //Then
        assertThat(resultado).isNotEmpty();
        assertThat(resultado).hasSize(1);
        assertThat(resultado).extracting(Course::getStatus).containsExactly("Publicado");

    }

    @Test
    @DisplayName("Course: Buscar cursos por activo")
    void shouldFindByActiveTrue(){
        //Given
        var instructor1 = instructorRepo.save(Instructor.builder().email("joleramirez@gmail.com")
                .fullName("José Luis Ramírez").createdAt(Instant.now()).updatedAt(Instant.now())
                .build());

        var curso1 = courseRepo.save(Course.builder().instructor(instructor1)
                .title("Introducción a la Programación en Java")
                .status("Publicado").active(true).createdAt(Instant.now()).updatedAt(Instant.now()).build());

        var curso2 = courseRepo.save(Course.builder().instructor(instructor1)
                .title("Introducción a bases de datos")
                .status("Borrador").active(false).createdAt(Instant.now()).updatedAt(Instant.now()).build());

        //When
        List<Course> resultado = courseRepo.findByActiveTrue();

        //Then
        assertThat(resultado).isNotEmpty();
        assertThat(resultado).hasSize(1);

    }

    @Test
    @DisplayName("Course: Buscar cursos por instructor")
    void shouldFindByInstructorId(){
        //Given
        var instructor1 = instructorRepo.save(Instructor.builder().email("joleramirez@gmail.com")
                .fullName("José Luis Ramírez").createdAt(Instant.now()).updatedAt(Instant.now())
                .build());

        var instructor2 = instructorRepo.save(Instructor.builder().email("mianpineda@gmail.com")
                .fullName("Miguel Ángel Pineda").createdAt(Instant.now()).updatedAt(Instant.now())
                .build());

        var curso1 = courseRepo.save(Course.builder().instructor(instructor1)
                .title("Introducción a la Programación en Java")
                .status("Publicado").active(true).createdAt(Instant.now()).updatedAt(Instant.now()).build());

        var curso2 = courseRepo.save(Course.builder().instructor(instructor2)
                .title("Introducción a la Programación en Java")
                .status("Borrador").active(false).createdAt(Instant.now()).updatedAt(Instant.now()).build());

        //When
        List<Course> resultado = courseRepo.findByInstructorId(instructor1.getId());

        //Then
        assertThat(resultado).isNotEmpty();
        assertThat(resultado).hasSize(1);
        assertThat(resultado).extracting(c -> c.getInstructor().getId()).containsExactly(instructor1.getId());
    }

}
