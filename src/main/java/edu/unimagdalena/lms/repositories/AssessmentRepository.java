package edu.unimagdalena.lms.repositories;

import edu.unimagdalena.lms.entities.Assessment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AssessmentRepository extends JpaRepository<Assessment, Long> {
    List<Assessment> findByCourseTitleIgnoreCase(String title);
}
