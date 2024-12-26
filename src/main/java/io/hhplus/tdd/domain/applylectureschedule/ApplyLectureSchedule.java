package io.hhplus.tdd.domain.applylectureschedule;

import io.hhplus.tdd.domain.lectureschedule.LectureSchedule;
import io.hhplus.tdd.domain.User.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ApplyLectureSchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id" , nullable = false , foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lecture_schedule_id",nullable = false , foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private LectureSchedule lectureSchedule;

    public ApplyLectureSchedule(User user , LectureSchedule lectureSchedule) {
        this.user = user;
        this.lectureSchedule = lectureSchedule;
    }
}
