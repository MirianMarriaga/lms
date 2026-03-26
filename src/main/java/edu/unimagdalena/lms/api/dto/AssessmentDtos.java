package edu.unimagdalena.lms.api.dto;
import java.io.Serializable;
import java.time.Instant;

public class AssessmentDtos {

    public record AssessmentCreateRequest(
            String type,
            int score,
            Long studentId,
            Long courseId
    ) implements Serializable {}

    public record AssessmentUpdateRequest(
            String type,
            Integer score
    ) implements Serializable {}

    public record AssessmentResponse(
            Long id,
            String type,
            int score,
            Long studentId,
            Long courseId,
            Instant takenAt
    ) implements Serializable {}
}