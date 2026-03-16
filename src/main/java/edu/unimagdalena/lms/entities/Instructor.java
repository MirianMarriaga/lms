package edu.unimagdalena.lms.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.Set;

@Entity
@Table(name = "instructors")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Instructor {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO) //podria ser GenerationType = UUID
    private long id;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "full_name", nullable = false)
    private String fullName;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;


    @OneToMany(mappedBy = "instructor", fetch = FetchType.LAZY)
    private Set<Course> courses;

    @OneToOne(mappedBy = "instructor", fetch = FetchType.EAGER)
    private InstructorProfile instructorProfile;

}
