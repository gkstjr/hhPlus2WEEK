package io.hhplus.tdd.domain.applylecture;

import io.hhplus.tdd.domain.Lecture.Lecture;
import io.hhplus.tdd.domain.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ApplyLecture {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne()
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lecture_id",nullable = false)
    private Lecture lecture;

    private LocalDateTime startTime;
    private LocalDateTime endTime;
}
