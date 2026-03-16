package edu.unimagdalena.lms.repositories;

import edu.unimagdalena.lms.entities.Enrollment;
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

public class EnrollmentRepositoryTest extends AbstractRepositoryIT{

    @Autowired
    EnrollmentRepository enrollmentRepo;
    @Autowired
    StudentRepository studentRepo;
    @Autowired
    CourseRepository courseRepo;
    @Autowired
    InstructorRepository instructorRepo;

    @Test
    @DisplayName("Enrollment: Encontrar inscripciónes por titulo de curso")

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


        enrollmentRepo.save(Enrollment.builder().student(estudante1).course(curso1).status("INSCRITO").enrolledAt(Instant.now()).build());
        enrollmentRepo.save(Enrollment.builder().student(estudante2).course(curso2).status("PENDIENTE").enrolledAt(Instant.now()).build());
        enrollmentRepo.save(Enrollment.builder().student(estudante3).course(curso1).status("PENDIENTE").enrolledAt(Instant.now()).build());
        //When

        List<Enrollment> resultado = enrollmentRepo.findByCourseTitleIgnoreCase("Introducción a bases de datos");

        //Then
        assertThat(resultado).isNotEmpty();
        assertThat(resultado).hasSize(1);
        assertThat(resultado).extracting(e -> e.getCourse().getTitle()).containsOnly(curso2.getTitle());

    }

}
