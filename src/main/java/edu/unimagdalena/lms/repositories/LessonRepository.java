package edu.unimagdalena.lms.repositories;

import edu.unimagdalena.lms.entities.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LessonRepository extends JpaRepository<Lesson, Long> {
    List<Lesson> findByTitleIgnoreCase(String title);

    List<Lesson> findByCourseTitleIgnoreCase(String title);
}
