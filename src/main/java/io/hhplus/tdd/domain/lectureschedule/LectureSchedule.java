package io.hhplus.tdd.domain.lectureschedule;

import io.hhplus.tdd.domain.Lecture.Lecture;
import io.hhplus.tdd.domain.applylectureschedule.ApplyLectureSchedule;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder(toBuilder = true)
@Entity
@ToString
public class LectureSchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //lectureInfo와 연관

    private int round;

    private int studentCount;
    //각 특강마다 초과 인원이 다를 수 있다고 가정하여 DB에 저장
    private int maxStudent;
    //시작,종료시간은 시간단위
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    @OneToMany(mappedBy = "lectureSchedule", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ApplyLectureSchedule> applyLectureSchedules = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lecture_id",nullable = false , foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Lecture lecture;
    //특강 신청
    public LectureSchedule apply() {
        if(isApplyPossible()) {
            studentCount++;
            return this;
        }else {
            throw new IllegalArgumentException("해당 특강의 신청 인원이 초과 되었습니다. ID : " + id);
        }
    }

    //재사용성 향상을 위해 함수로 빼기
    public boolean isApplyPossible() {
        return studentCount < maxStudent;
    }
    
    //동일한 신청자는 동일한 주제와 강사의 특강을 신청할 수 없다.
    public void validateDuplicateLecture(List<ApplyLectureSchedule> applyList) {
        if(applyList.stream().anyMatch(apply -> apply.getLectureSchedule().getLecture().getId().equals(this.lecture.getId()))) {
            throw new IllegalArgumentException("동일한 신청자가 동일한 강의를 신청할 수 없습니다.");
        }
    }

    //신청자가 이미 같은 날짜 시간에 다른 특강 신청이 되어 있으면 예외 처리
    public void validateDuplicateTime(List<ApplyLectureSchedule> applyList) {
        if(applyList.stream().anyMatch(apply -> this.isDuplicateTime(apply.getLectureSchedule()))) {
            throw new IllegalArgumentException("동일시간대에 여러 개의 특강을 신청할 수 없습니다.");
        };
    }

    private boolean isDuplicateTime(LectureSchedule schedule) {
            return !(this.endTime.isBefore(schedule.getStartTime()) || this.startTime.isAfter(schedule.getEndTime()));
    }

    public boolean isAfter(LocalDateTime time) {
        return startTime.isAfter(time);
    }

}
