package io.hhplus.tdd.service;

import io.hhplus.tdd.domain.ApplyLecture;
import io.hhplus.tdd.domain.Lecture;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Objects;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class LectureApplicationServiceTest {

    @Mock
    private LectureRepository lectureRepository;
    @InjectMocks
    private LectureApplicationService lectureService;

    @Test
    public void 특강신청완료시_ApplyLecture_반환() {
        //when
        Lecture lecture = Lecture.builder()
                            .id(1L)
                            .studentCount(10)
                            .maxStudent(30)
                            .build();
        //given
        Mockito.when(lectureRepository.save(lecture.getId())).thenReturn(new ApplyLecture());
        //then
    }
}
