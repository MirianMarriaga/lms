package edu.unimagdalena.lms.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Table(name = "enrollments")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Enrollment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    // student_id  FK---------------------------------------
    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;
    //-----------------------------------------------------

    // course_id  FK---------------------------------------
    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;
    //-----------------------------------------------------

    private String status;

    @Column(name= "enrolled_at", nullable = false)
    private Instant enrolledAt;
}
