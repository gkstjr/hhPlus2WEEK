package io.hhplus.tdd.service;

import io.hhplus.tdd.domain.Lecture.ILectureRepository;
import io.hhplus.tdd.domain.User;
import io.hhplus.tdd.domain.applylecture.ApplyLecture;
import io.hhplus.tdd.domain.Lecture.Lecture;
import io.hhplus.tdd.domain.LectureApplicationService;
import io.hhplus.tdd.domain.Lecture.dto.LectureApplyCommand;
import io.hhplus.tdd.domain.applylecture.IApplyLectureRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import static org.assertj.core.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
public class LectureApplicationServiceTest {

    @Mock
    private ILectureRepository iLectureRepository;
    @Mock
    private IApplyLectureRepository iApplyLectureRepository;
    @InjectMocks
    private LectureApplicationService lectureService;

    @Test
    public void 없는_특강_신청시_IllegalArgumentException_반환() {
        //given
        long lectureId = 1L;

        Mockito.when(iLectureRepository.findById(lectureId)).thenReturn(Optional.empty());
        //when
        //then
        assertThatThrownBy(() -> lectureService.apply(new LectureApplyCommand(lectureId , 1)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("신청하신 특강이 없습니다. lectureId : " + lectureId);
    }

    @Test//TODO Q&A 이후 동일한의 기준 정하고 수정 필요할 수도?
    public void 특강신청시_동일_신청자가_동일_강의_신청시_IllegalArgumentException_반환() {
        //given
        long lectureId = 1;
        long userId = 1;

        Mockito.when(iLectureRepository.findById(lectureId)).thenReturn(Optional.of(new Lecture()));
        Mockito.when(iApplyLectureRepository.existsByUserIdAndLectureId(userId , lectureId)).thenReturn(true);
        //when

        //then
        assertThatThrownBy(() -> lectureService.apply(new LectureApplyCommand(lectureId , userId)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("동일한 신청자가 동일한 강의를 신청할 수 없습니다.");
    }

    @Test
    public void 같은시간에_동일신청자가_다른특강신청기록이있으면_IllegalArgumentException반환() {
        //given
        long userId = 1;
        long lectureId = 1;

        Mockito.when(iLectureRepository.findById(lectureId)).thenReturn(Optional.of(new Lecture()));
        Mockito.when(iApplyLectureRepository.existsByUserIdAndLectureId(userId, lectureId)).thenReturn(false);
//        Mockito.when(iApplyLectureRepository.existsByUserIdAndLectureEndTimeGreaterThanAndLectureStartTimeLessThan)
        //when
        //then

    }
    @Test
    public void 특강신청완료시_ApplyLecture_반환() {
        //given
        Lecture lecture = Lecture.builder()
                            .id(1L)
                            .studentCount(10)
                            .maxStudent(30)
                            .build();

       // Mockito.when(ILectureRepository.save(lecture.getId())).thenReturn(Optional.of(new ApplyLecture(1L)));
        //when

        //then
    }
}
