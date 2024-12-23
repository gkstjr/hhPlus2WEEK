package io.hhplus.tdd.domain;

import io.hhplus.tdd.domain.Lecture.Lecture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import static org.assertj.core.api.Assertions.*;

public class LectureTest {

    private Lecture lecture1;
    private Lecture lecture2;

    @BeforeEach
    void setUp() {
        lecture1 = Lecture.builder()
                .id(1L)
                .round(1)
                .studentCount(20)
                .maxStudent(30)
                .dateTime(LocalDateTime.now())
                .build();

        lecture2 = Lecture.builder()
                .id(1L)
                .round(1)
                .studentCount(29)
                .maxStudent(30)
                .dateTime(LocalDateTime.now())
                .build();
    }

    @Test
    public void 선착순30명초과_특강신청시_IllegalArgument예외반환() {
        //given
        lecture1 = lecture1.toBuilder()
                .studentCount(30)
                .build();
        //when
        //then
        assertThatThrownBy(() -> lecture1.apply())
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("해당 특강의 신청 인원이 초과 되었습니다. ID : " + lecture1.getId());
    }

    @Test
    public void 선착순30명이하_특강신청시_Lecture객체반환() {
        //given
        //when
        Lecture result1 = lecture1.apply();
        Lecture result2 = lecture2.apply();

        //then
        assertThat(result1.getStudentCount()).isEqualTo(21);
        assertThat(result2.getStudentCount()).isEqualTo(30);
    }
}
