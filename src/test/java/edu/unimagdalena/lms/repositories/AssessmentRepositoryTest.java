package edu.unimagdalena.lms.repositories;


import edu.unimagdalena.lms.entities.Assessment;
import edu.unimagdalena.lms.entities.Student;
import edu.unimagdalena.lms.entities.Course;
import edu.unimagdalena.lms.entities.Instructor;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Instant;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

public class AssessmentRepositoryTest extends AbstractRepositoryIT{

    @Autowired
    AssessmentRepository assessmentRepo;
    @Autowired
    StudentRepository studentRepo;
    @Autowired
    CourseRepository courseRepo;
    @Autowired
    InstructorRepository instructorRepo;

    @Test
    @DisplayName("Assessment: Encontrar evaluaciónes por titulo de curso")
    void shouldFindByCourseTitleIgnoreCase(){
        //Given
        var estudante1 = studentRepo.save(Student.builder()
                .email("jamanjarrez@gmail.com")
                .fullName("Juan Andres Manjarrez")
                .createdAt(Instant.now())
                .updatedAt(Instant.now())
                .build());

        var estudante2 = studentRepo.save(Student.builder()
                .email("mflopez@example.com")
                .fullName("Maria Fernanda Lopez")
                .createdAt(Instant.now())
                .updatedAt(Instant.now())
                .build());

        var estudante3 = studentRepo.save(Student.builder()
                .email("cevalencia@example.com")
                .fullName("Carlos Eduardo Valencia")
                .createdAt(Instant.now())
                .updatedAt(Instant.now())
                .build());

        var instructor = instructorRepo.save(Instructor.builder().email("joleramirez@gmail.com")
                .fullName("José Luis Ramírez").createdAt(Instant.now()).updatedAt(Instant.now())
                .build());

        var curso1 = courseRepo.save(Course.builder().instructor(instructor)
                .title("Introducción a la Programación en Java")
                .status("Publicado").active(true).createdAt(Instant.now()).updatedAt(Instant.now()).build());
        var curso2 = courseRepo.save(Course.builder().instructor(instructor)
                .title("Introducción a bases de datos")
                .status("Borrador").active(false).createdAt(Instant.now()).updatedAt(Instant.now()).build());

        assessmentRepo.save(Assessment.builder().student(estudante1).course(curso1).type("EXAMEN").score(85).takenAt(Instant.now()).build());
        assessmentRepo.save(Assessment.builder().student(estudante2).course(curso1).type("EXAMEN").score(60).takenAt(Instant.now()).build());
        assessmentRepo.save(Assessment.builder().student(estudante3).course(curso2).type("TALLER").score(50).takenAt(Instant.now()).build());

        //When
        List<Assessment> resultado = assessmentRepo.findByCourseTitleIgnoreCase("INTRODUCCIÓN A LA PROGRAMACIÓN EN JAVA");

        //Then
        assertThat(resultado).isNotEmpty();
        assertThat(resultado).hasSize(2);
        assertThat(resultado).extracting(e -> e.getCourse().getTitle()).containsOnly(curso1.getTitle());

    }

}
