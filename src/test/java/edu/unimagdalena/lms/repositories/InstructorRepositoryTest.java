package edu.unimagdalena.lms.repositories;

import edu.unimagdalena.lms.entities.Instructor;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Instant;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

public class InstructorRepositoryTest extends AbstractRepositoryIT{

    @Autowired
    InstructorRepository instructorRepo;

    @Test
    @DisplayName("Instructor: encuentra por email ignorando mayúsculas")
    void shouldFindByEmailIgnoreCase(){
        //Give
        instructorRepo.save(Instructor.builder()
                .email("malopez@gmail.com")
                .fullName("Maria Andrea Lopez")
                .createdAt(Instant.now())
                .updatedAt(Instant.now())
                .build());

        //When

        Optional<Instructor> resultado = instructorRepo.findByEmailIgnoreCase("malopez@gmail.com");

        //Then
        assertThat(resultado).isPresent();
        assertThat(resultado.get().getEmail()).isEqualTo("malopez@gmail.com");
    }
}
