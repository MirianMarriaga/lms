package edu.unimagdalena.lms.repositories;

import edu.unimagdalena.lms.entities.Course;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourseRepository extends JpaRepository<Course, Long> {
    List<Course> findByTitleIgnoreCase(String title);

    List<Course> findByStatus(String status);

    List<Course> findByActiveTrue();

    List<Course> findByInstructorId(long idInstructor);

}
