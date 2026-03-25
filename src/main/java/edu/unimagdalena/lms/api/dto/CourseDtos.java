package edu.unimagdalena.lms.api.dto;
import java.io.Serializable;
import java.time.Instant;

public class CourseDtos {
    public record CourseCreateRequest(
            long instructorId,
            String title,
            String status,
            boolean active
    ) implements Serializable {}

    public record CourseUpdateRequest(
            long InstructorId,
            String title,
            String status,
            boolean active
    ) implements Serializable {}

    public record CourseResponse(
            long id,
            String title,
            String status,
            boolean active,
            Instant createdAt,
            Instant updatedAt,
            String instructorName
    ) implements Serializable {}
}
