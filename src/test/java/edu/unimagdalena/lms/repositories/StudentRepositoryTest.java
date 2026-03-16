package edu.unimagdalena.lms.repositories;

import edu.unimagdalena.lms.entities.Student;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Instant;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

public class StudentRepositoryTest extends AbstractRepositoryIT{

    @Autowired
    StudentRepository studentRepo;

    @Test
    @DisplayName("Student: encuentra por email ignorando mayúsculas")
    void shouldFindByEmailIgnoreCase() {
        //Given
        studentRepo.save(Student.builder()
                .email("jamanjarrez@gmail.com")
                .fullName("Juan Andres Manjarrez")
                .createdAt(Instant.now())
                .updatedAt(Instant.now())
                .build());

        //When
        Optional <Student> resultado = studentRepo.findByEmailIgnoreCase("jamanjarrez@gmail.com");

        //Then
        assertThat(resultado).isPresent();
        assertThat(resultado.get().getEmail()).isEqualTo("jamanjarrez@gmail.com");
    }
}


