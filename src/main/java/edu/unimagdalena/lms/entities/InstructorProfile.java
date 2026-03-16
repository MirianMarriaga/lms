package edu.unimagdalena.lms.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Table(name = "instructor_profiles")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class InstructorProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    // instructor_id FK------------------------
    @OneToOne
    @JoinColumn(name = "instructor_id")
    private Instructor instructor;
    //-----------------------------------------

    @Column(name="phone", nullable = false)
    private String phone;

    private String bio;

}
