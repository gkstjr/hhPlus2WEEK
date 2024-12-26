package io.hhplus.tdd.service;

import io.hhplus.tdd.domain.Lecture.Lecture;
import io.hhplus.tdd.domain.User.IUserRepository;
import io.hhplus.tdd.domain.User.User;
import io.hhplus.tdd.domain.applylectureschedule.ApplyLectureSchedule;
import io.hhplus.tdd.domain.lectureschedule.ILectureScheduleRepository;
import io.hhplus.tdd.domain.lectureschedule.LectureSchedule;
import io.hhplus.tdd.domain.applylectureschedule.ApplyLectureScheduleService;
import io.hhplus.tdd.domain.applylectureschedule.dto.LectureApplyCommand;
import io.hhplus.tdd.domain.applylectureschedule.IApplyLectureScheduleRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.assertj.core.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
public class ApplyLectureScheduleServiceTest {

    @Mock
    private ILectureScheduleRepository iLectureScheduleRepository;
    @Mock
    private IApplyLectureScheduleRepository iApplyLectureScheduleRepository;
    @Mock
    private IUserRepository iUserRepository;
    @InjectMocks
    private ApplyLectureScheduleService lectureScheduleService;

    @Test
    public void 없는_특강_신청시_IllegalArgumentException_반환() {
        //given
        long lectureId = 1L;

        Mockito.when(iLectureScheduleRepository.findById(lectureId)).thenReturn(Optional.empty());
        //when
        //then
        assertThatThrownBy(() -> lectureScheduleService.apply(new LectureApplyCommand(lectureId , 1)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("신청하신 특강이 없습니다. lectureId : " + lectureId);
    }

    @Test//TODO Q&A 이후 동일한의 기준 정하고 수정 필요할 수도?
    public void 특강신청시_동일_신청자가_동일_강의_신청시_IllegalArgumentException_반환() {
        //given
        User user = User.builder()
                 .id(1L)
                 .build();
        Lecture requestLecture = Lecture.builder()
                 .id(1L)
                 .build();
        LectureSchedule requestSchedule = LectureSchedule.builder()
                 .id(1L)
                 .lecture(requestLecture)
                 .build();
        LectureSchedule duplicatedSchedule = LectureSchedule.builder()
                .id(2L)
                .lecture(requestLecture) //동일 강의 이미 신청
                .build();
        ApplyLectureSchedule duplicatedApplySchedule = ApplyLectureSchedule.builder()
                .id(1L)
                .lectureSchedule(duplicatedSchedule)
                .user(user)
                .build();

        List<ApplyLectureSchedule> list = new ArrayList<>();
        list.add(duplicatedApplySchedule);

        Mockito.when(iLectureScheduleRepository.findById(1L)).thenReturn(Optional.of(requestSchedule)); //회차별 특정 강의 조회
        Mockito.when(iApplyLectureScheduleRepository.findAllByUserId(user.getId())).thenReturn(list);
        Mockito.when(iUserRepository.findById(user.getId())).thenReturn(Optional.of(user));


        //when
        //then
        assertThatThrownBy(() -> lectureScheduleService.apply(new LectureApplyCommand(user.getId() , requestLecture.getId())))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("동일한 신청자가 동일한 강의를 신청할 수 없습니다.");
    }

    @Test
    public void 같은시간에_동일신청자가_다른특강신청기록이있으면_IllegalArgumentException반환() {
        //given
        long userId = 1;
        long lectureId = 1;
        long lectureScheduleId = 1L;
        Lecture requestlecture = Lecture.builder()
                .id(lectureId)
                .build();
        LectureSchedule requestSchedule = LectureSchedule.builder()
                .id(lectureScheduleId)
                .lecture(requestlecture)
                .startTime(LocalDateTime.now().minusMinutes(60))
                .endTime(LocalDateTime.now().plusHours(1))
                .build();
        LectureSchedule duplicatedTimeSchedule = LectureSchedule.builder()
                .id(2L)
                .startTime(LocalDateTime.now())
                .endTime(LocalDateTime.now().plusHours(1))
                .lecture(Lecture.builder().id(2L).build())
                .build();
        ApplyLectureSchedule duplicatedApplySchedule = ApplyLectureSchedule.builder()
                .id(1L)
                .lectureSchedule(duplicatedTimeSchedule)
                .user(User.builder().id(userId).build())
                .build();

        List<ApplyLectureSchedule> list = new ArrayList<>();
        list.add(duplicatedApplySchedule);

        Mockito.when(iLectureScheduleRepository.findById(lectureScheduleId)).thenReturn(Optional.of(requestSchedule));
        Mockito.when(iApplyLectureScheduleRepository.findAllByUserId(userId)).thenReturn(list);
        Mockito.when(iUserRepository.findById(userId)).thenReturn(Optional.of(new User()));
        //when
        //then
        assertThatThrownBy(() -> lectureScheduleService.apply(new LectureApplyCommand(userId , lectureScheduleId)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("동일시간대에 여러 개의 특강을 신청할 수 없습니다.");
    }
    @Test
    public void 선착순30명초과_특강신청시_IllegalArgument예외반환() {
        //given
        int studentCount = 30;
        User user = User.builder()
                .id(1L)
                .build();
        Lecture requestLecture = Lecture.builder()
                .id(1L)
                .build();
        Lecture existingLecture = Lecture.builder()
                .id(2L)
                .build();

        LectureSchedule requestSchedule = LectureSchedule.builder()
                .id(1L)
                .lecture(requestLecture)
                .maxStudent(30)
                .studentCount(studentCount)
                .startTime(LocalDateTime.now())
                .endTime(LocalDateTime.now().plusHours(1))
                .build();
        LectureSchedule existingSchedule = LectureSchedule.builder()
                .id(2L)
                .lecture(existingLecture)
                .startTime(LocalDateTime.now().plusHours(2))
                .endTime(LocalDateTime.now().plusHours(3))
                .build();

        ApplyLectureSchedule existingApplySchedule = ApplyLectureSchedule.builder()
                .id(1L)
                .lectureSchedule(existingSchedule)
                .user(user)
                .build();
        List<ApplyLectureSchedule> list = new ArrayList<>();
        list.add(existingApplySchedule);

        Mockito.when(iLectureScheduleRepository.findById(requestSchedule.getId())).thenReturn(Optional.of(requestSchedule));
        Mockito.when(iApplyLectureScheduleRepository.findAllByUserId(user.getId())).thenReturn(list);
        Mockito.when(iUserRepository.findById(user.getId())).thenReturn(Optional.of(user));

        //when
        assertThatThrownBy(() -> lectureScheduleService.apply(new LectureApplyCommand(user.getId() , requestSchedule.getId())))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("해당 특강의 신청 인원이 초과 되었습니다. ID : " + requestSchedule.getId());
    }

    @Test
    public void 특강신청시_studentCount필드_1추가_ApplyLectureSchedule반환_성공() {
        //given
        int studentCount = 20;
        User user = User.builder()
                .id(1L)
                .build();
        Lecture requestLecture = Lecture.builder()
                .id(1L)
                .build();
        Lecture existingLecture = Lecture.builder()
                .id(2L)
                .build();

        LectureSchedule requestSchedule = LectureSchedule.builder()
                .id(1L)
                .lecture(requestLecture)
                .maxStudent(30)
                .studentCount(studentCount)
                .startTime(LocalDateTime.now())
                .endTime(LocalDateTime.now().plusHours(1))
                .build();
        LectureSchedule existingSchedule = LectureSchedule.builder()
                .id(2L)
                .lecture(existingLecture)
                .startTime(LocalDateTime.now().plusHours(2))
                .endTime(LocalDateTime.now().plusHours(3))
                .build();

        ApplyLectureSchedule existingApplySchedule = ApplyLectureSchedule.builder()
                .id(1L)
                .lectureSchedule(existingSchedule)
                .user(user)
                .build();
        List<ApplyLectureSchedule> list = new ArrayList<>();
        list.add(existingApplySchedule);

        Mockito.when(iLectureScheduleRepository.findById(requestSchedule.getId())).thenReturn(Optional.of(requestSchedule));
        Mockito.when(iApplyLectureScheduleRepository.findAllByUserId(user.getId())).thenReturn(list);
        Mockito.when(iUserRepository.findById(user.getId())).thenReturn(Optional.of(user));
        Mockito.when(iApplyLectureScheduleRepository.save(Mockito.any(ApplyLectureSchedule.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        //when
        ApplyLectureSchedule successApply = lectureScheduleService.apply(new LectureApplyCommand(user.getId() , requestSchedule.getId()));
        //then
        assertThat(successApply.getLectureSchedule().getStudentCount()).isEqualTo(studentCount + 1);
    }
}
