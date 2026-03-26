package edu.unimagdalena.lms.api.dto;
import java.io.Serializable;
import java.time.Instant;

public class InstructorDtos {
    public record InstructorProfileDto(
            String phone,
            String bio
    ) implements Serializable {}

    public record InstructorCreateRequest(
            String email,
            String fullName,
            InstructorProfileDto profile
    ) implements Serializable {}

    public record InstructorUpdateRequest(
            String email,
            String fullName,
            InstructorProfileDto profile
    ) implements Serializable {}

    public record InstructorResponse(
            Long id,
            String email,
            String fullName,
            Instant createdAt,
            Instant updatedAt,
            InstructorProfileDto profile
    ) implements Serializable {}
}
