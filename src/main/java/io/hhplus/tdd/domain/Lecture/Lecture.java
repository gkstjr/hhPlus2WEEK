package io.hhplus.tdd.domain.Lecture;

import io.hhplus.tdd.domain.applylecture.ApplyLecture;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder(toBuilder = true)
@Entity
public class Lecture {

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


    @OneToMany(mappedBy = "lecture", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ApplyLecture> applyLectures = new ArrayList<>();
    //특강 신청
    public Lecture apply() {
        if(isPossiable()) {
            studentCount++;
            return this;
        }else {
            throw new IllegalArgumentException("해당 특강의 신청 인원이 초과 되었습니다. ID : " + id);
        }
    }

    //재사용성 향상을 위해 함수로 빼기
    public boolean isPossiable() {
        return studentCount < maxStudent;
    }
}
