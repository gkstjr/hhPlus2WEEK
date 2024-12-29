package io.hhplus.tdd.domain;

import io.hhplus.tdd.domain.lectureschedule.LectureSchedule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import static org.assertj.core.api.Assertions.*;

public class LectureScheduleTest {

    private LectureSchedule lectureSchedule1;
    private LectureSchedule lectureSchedule2;

    @BeforeEach
    void setUp() {
        lectureSchedule1 = LectureSchedule.builder()
                .id(1L)
                .round(1)
                .studentCount(20)
                .maxStudent(30)
                .startTime(LocalDateTime.now())
                .endTime(LocalDateTime.now().plusHours(1))
                .build();

        lectureSchedule2 = LectureSchedule.builder()
                .id(1L)
                .round(1)
                .studentCount(29)
                .maxStudent(30)
                .startTime(LocalDateTime.now())
                .endTime(LocalDateTime.now().plusHours(1))
                .build();
    }

    @Test
    public void 선착순30명초과_특강신청시_IllegalArgument예외반환() {
        //given
        lectureSchedule1 = lectureSchedule1.toBuilder()
                .studentCount(30)
                .build();
        //when
        //then
        assertThatThrownBy(() -> lectureSchedule1.apply())
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("해당 특강의 신청 인원이 초과 되었습니다. ID : " + lectureSchedule1.getId());
    }

    @Test
    public void 선착순30명이하_특강신청시_Lecture객체반환() {
        //given
        //when
        LectureSchedule result1 = lectureSchedule1.apply();
        LectureSchedule result2 = lectureSchedule2.apply();

        //then
        assertThat(result1.getStudentCount()).isEqualTo(21);
        assertThat(result2.getStudentCount()).isEqualTo(30);
    }
}
