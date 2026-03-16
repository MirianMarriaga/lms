package edu.unimagdalena.lms.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Table(name = "assessments")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Assessment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    // student_id FK ---------------------------------
    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;
    //----------------------------------------------

    // course_id -------------------------------------
    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;
    //--------------------------------------------------

    @Column(name = "type", nullable = false)
    private String type;

    private int score;

    @Column(name = "taken_at", nullable = false)
    private Instant takenAt;
}

