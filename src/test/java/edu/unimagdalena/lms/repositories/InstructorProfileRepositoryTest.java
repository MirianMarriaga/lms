package edu.unimagdalena.lms.repositories;

import edu.unimagdalena.lms.entities.InstructorProfile;
import edu.unimagdalena.lms.entities.Instructor;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Instant;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

public class InstructorProfileRepositoryTest extends AbstractRepositoryIT{

    @Autowired
    InstructorProfileRepository instructorProfileRepo;
    @Autowired
    InstructorRepository instructorRepo;

    @Test
    @DisplayName("InstructorProfile: encuentra instructorProfiles por emails ignorando las mayscúlas")

    void shouldFindByInstructorEmailIgnoreCase(){
        //Given

        var instructor = instructorRepo.save(Instructor.builder().email("ladarojas@gmail.com").fullName("Laura Daniela Rojas")
                .createdAt(Instant.now()).updatedAt(Instant.now()).build());

        var instructorProfile  = InstructorProfile.builder().instructor(instructor).phone("+57 318 672 9451").
                bio("Profesora dedicada a crear un ambiente de aprendizaje dinámico y participativo. Le gusta" +
                        " fomentar la curiosidad de sus estudiantes mediante proyectos y actividades prácticas " +
                        "que conectan la teoría con la vida diaria.").build();
        instructorProfileRepo.save(instructorProfile);

        // When
        Optional<InstructorProfile> byEmail = instructorProfileRepo.findByInstructorEmailIgnoreCase("LADAROJAS@GMAIL.COM");

        // Then
        assertThat(byEmail).isPresent();
        assertThat(byEmail.get().getInstructor().getEmail()).isEqualTo("ladarojas@gmail.com");

    }
}
