package edu.unimagdalena.lms.api.dto;
import java.io.Serializable;
import java.time.Instant;

public class CourseDtos {
    public record CourseCreateRequest(
            Long instructorId,
            String title,
            String status,
            Boolean active
    ) implements Serializable {}

    public record CourseUpdateRequest(
            Long instructorId,
            String title,
            String status,
            Boolean active
    ) implements Serializable {}

    public record CourseResponse(
            long id,
            String title,
            String status,
            Boolean active,
            Instant createdAt,
            Instant updatedAt,
            String instructorName
    ) implements Serializable {}
}
