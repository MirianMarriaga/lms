package edu.unimagdalena.lms.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "lessons")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter

public class Lesson {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    //Course id-----FK --------------
    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;
    //-------------------------------
    private String title;
    @Column(name = "order_index")
    private int orderIndex;
}
