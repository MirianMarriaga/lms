package edu.unimagdalena.lms.entities;

import jakarta.persistence.*;
import jakarta.transaction.UserTransaction;
import lombok.*;

import java.time.Instant;
import java.util.Set;

@Entity
@Table(name = "courses")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    //---Instructor FK-----------------------------
    @ManyToOne
    @JoinColumn(name = "instructor_id")
    private Instructor instructor;
    //---------------------------------------------

    @Column(name = "title", nullable = false)
    private String title;

    private String status;

    @Column(name = "active", nullable = false)
    private boolean active;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    //-----------------------------mappedBy-------------------

    @OneToMany(mappedBy = "course", fetch = FetchType.LAZY)
    private Set<Lesson> lessons;

    @OneToMany(mappedBy = "course", fetch = FetchType.LAZY)
    private Set<Enrollment> enrollments;

    @OneToMany(mappedBy = "course", fetch = FetchType.LAZY)
    private Set<Assessment> assessments;
}
