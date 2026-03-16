package edu.unimagdalena.lms.repositories;

import edu.unimagdalena.lms.entities.Instructor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InstructorRepository extends JpaRepository<Instructor, Long> {
    Optional<Instructor> findByEmailIgnoreCase(String email);
}
