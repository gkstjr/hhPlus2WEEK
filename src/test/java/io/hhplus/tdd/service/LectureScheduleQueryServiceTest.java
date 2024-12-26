package io.hhplus.tdd.service;

import io.hhplus.tdd.domain.Lecture.Lecture;
import io.hhplus.tdd.domain.User.IUserRepository;
import io.hhplus.tdd.domain.applylectureschedule.ApplyLectureScheduleService;
import io.hhplus.tdd.domain.applylectureschedule.IApplyLectureScheduleRepository;
import io.hhplus.tdd.domain.lectureschedule.ILectureScheduleRepository;
import io.hhplus.tdd.domain.lectureschedule.LectureSchedule;
import io.hhplus.tdd.domain.lectureschedule.LectureScheduleQueryService;
import io.hhplus.tdd.domain.lectureschedule.dto.LectureQueryCommand;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
public class LectureScheduleQueryServiceTest {

    @InjectMocks
    private LectureScheduleQueryService queryService;
    @Mock
    private ILectureScheduleRepository iLectureScheduleRepository;
    @Test
    public void 해당날짜의_특강목록이_없으면_빈_list반환() {
        //given
        LocalDate givenDay = LocalDate.now();

        Mockito.when(iLectureScheduleRepository.findAllByDate(givenDay)).thenReturn(Collections.emptyList());
        //when
        List<LectureSchedule> getSchedules =  queryService.getSchedulesByDate(new LectureQueryCommand(givenDay));
        //then
        assertThat(getSchedules).isEmpty();
    }

    @Test
    public void 해당날짜의_현재시간보다이전_시작강의만_있으면_빈_list반환() {
        //given
        LocalDate givenDay = LocalDate.now();
        LectureSchedule schedule1 = LectureSchedule.builder()
                .id(1L)
                .lecture(Lecture.builder().build())
                .startTime(LocalDateTime.now().minusMinutes(50))
                .endTime(LocalDateTime.now().plusHours(2))
                .build();
        LectureSchedule schedule2 = LectureSchedule.builder()
                .id(2L)
                .lecture(Lecture.builder().build()) //동일 강의 이미 신청
                .startTime(LocalDateTime.now().minusHours(2))
                .endTime(LocalDateTime.now())
                .build();

        List<LectureSchedule> list = new ArrayList<>();
        list.add(schedule1); list.add(schedule2);

        Mockito.when(iLectureScheduleRepository.findAllByDate(givenDay)).thenReturn(list);

        ///when
        List<LectureSchedule> getSchedules =  queryService.getSchedulesByDate(new LectureQueryCommand(givenDay));

        assertThat(getSchedules).isEmpty();
    }

    @Test
    public void 수강인원이_꽉_찬_강의만있으면_빈목록반환() {
        //given
        LocalDate givenDay = LocalDate.now();
        LectureSchedule schedule1 = LectureSchedule.builder()
                .id(1L)
                .lecture(Lecture.builder().build())
                .maxStudent(30)
                .studentCount(30)
                .startTime(LocalDateTime.now().plusHours(1))
                .endTime(LocalDateTime.now().plusHours(2))
                .build();
        LectureSchedule schedule2 = LectureSchedule.builder()
                .id(2L)
                .maxStudent(30)
                .studentCount(30)
                .lecture(Lecture.builder().build())
                .startTime(LocalDateTime.now().plusHours(1))
                .endTime(LocalDateTime.now().plusHours(2))
                .build();

        List<LectureSchedule> list = new ArrayList<>();
        list.add(schedule1); list.add(schedule2);

        Mockito.when(iLectureScheduleRepository.findAllByDate(givenDay)).thenReturn(list);

        ///when
        List<LectureSchedule> getSchedules =  queryService.getSchedulesByDate(new LectureQueryCommand(givenDay));

        assertThat(getSchedules).isEmpty();
    }

    @Test
    public void 수강인원이_꽉_차지않고_현재시간이후강의_목록반환_성공테스트() {
        //given
        LocalDate givenDay = LocalDate.now();
        LectureSchedule schedule1 = LectureSchedule.builder()
                .id(1L)
                .lecture(Lecture.builder().build())
                .maxStudent(30)
                .studentCount(29)
                .startTime(LocalDateTime.now().plusHours(1))
                .endTime(LocalDateTime.now().plusHours(2))
                .build();
        LectureSchedule schedule2 = LectureSchedule.builder()
                .id(2L)
                .maxStudent(30)
                .studentCount(28)
                .lecture(Lecture.builder().build())
                .startTime(LocalDateTime.now().plusHours(1))
                .endTime(LocalDateTime.now().plusHours(2))
                .build();

        List<LectureSchedule> list = new ArrayList<>();
        list.add(schedule1); list.add(schedule2);

        Mockito.when(iLectureScheduleRepository.findAllByDate(givenDay)).thenReturn(list);

        ///when
        List<LectureSchedule> getSchedules =  queryService.getSchedulesByDate(new LectureQueryCommand(givenDay));

        assertThat(getSchedules).hasSize(2);
        assertThat(getSchedules.get(0).getId()).isEqualTo(schedule1.getId());
        assertThat(getSchedules.get(0).getStartTime()).isEqualTo(schedule1.getStartTime());
        assertThat(getSchedules.get(1).getId()).isEqualTo(schedule2.getId());
        assertThat(getSchedules.get(1).getStartTime()).isEqualTo(schedule2.getStartTime());    }
}
