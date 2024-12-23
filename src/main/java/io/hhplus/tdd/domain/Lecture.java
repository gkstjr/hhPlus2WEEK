package io.hhplus.tdd.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

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

    private LocalDateTime dateTime;

    //특강 신청
    public Lecture apply() {
        if(studentCount < maxStudent) {
            studentCount++;
            return this;
        }else {
            throw new IllegalArgumentException("해당 특강의 신청 인원이 초과 되었습니다. ID : " + id);
        }
    }
}
