package edu.unimagdalena.lms.api.dto;
import java.io.Serializable;
import java.time.Instant;

public class EnrollmentDtos {

    public record EnrollmentCreateRequest(
            Long studentId,
            Long courseId,
            String status
    ) implements Serializable {}

    public record EnrollmentUpdateRequest(
            String status
    ) implements Serializable {}

    public record EnrollmentResponse(
            Long id,
            Long studentId,
            Long courseId,
            String status,
            Instant enrolledAt
    ) implements Serializable {}
}