package io.hhplus.tdd;

import io.hhplus.tdd.domain.Lecture.ILectureRepository;
import io.hhplus.tdd.domain.Lecture.Lecture;
import io.hhplus.tdd.domain.User.IUserRepository;
import io.hhplus.tdd.domain.User.User;
import io.hhplus.tdd.domain.applylectureschedule.ApplyLectureSchedule;
import io.hhplus.tdd.domain.applylectureschedule.ApplyLectureScheduleService;
import io.hhplus.tdd.domain.applylectureschedule.IApplyLectureScheduleRepository;
import io.hhplus.tdd.domain.lectureschedule.ILectureScheduleRepository;
import io.hhplus.tdd.domain.lectureschedule.LectureSchedule;
import io.hhplus.tdd.domain.applylectureschedule.dto.LectureApplyCommand;
import io.hhplus.tdd.domain.lectureschedule.LectureScheduleQueryService;
import io.hhplus.tdd.domain.lectureschedule.dto.LectureQueryCommand;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
public class LectureIntegrationTest {

    @Autowired
    private ApplyLectureScheduleService service;
    @Autowired
    private IApplyLectureScheduleRepository applyScheduleRepo;
    @Autowired
    private ILectureScheduleRepository scheduleRepo;
    @Autowired
    private IUserRepository userRepository;
    @Autowired
    private ILectureRepository lectureRepo;
    @Autowired
    private LectureScheduleQueryService scheduleQueryService;

    @Test
    @DirtiesContext
    public void 특강신청_통합테스트() {
        //given
        int initialStudentCnt = 20;
        User user = User.builder()
                .name("기만석")
                .build();

        Lecture requestLecture = Lecture.builder()
                .title("미스터킴의 소름끼치는 자바 특강")
                .teacher("미스터킴")
                .build();
        LectureSchedule requestSchedule = LectureSchedule.builder()
                .lecture(requestLecture)
                .round(1)
                .studentCount(initialStudentCnt)
                .maxStudent(30)
                .startTime(LocalDateTime.now())
                .endTime(LocalDateTime.now().plusHours(1))
                .build();

        Lecture existingLecture = Lecture.builder()
                .title("디자인 패턴이 궁금해? 내게 와")
                .teacher("패턴킹")
                .build();
        LectureSchedule existingSchedule = LectureSchedule.builder()
                .lecture(existingLecture)
                .round(1)
                .studentCount(10)
                .maxStudent(30)
                .startTime(LocalDateTime.now().plusHours(3))
                .endTime(LocalDateTime.now().plusHours(4))
                .build();
        lectureRepo.save(requestLecture);
        lectureRepo.save(existingLecture);
        scheduleRepo.save(requestSchedule);
        scheduleRepo.save(existingSchedule);
        userRepository.save(user);

        ApplyLectureSchedule existingApplySchedule = ApplyLectureSchedule.builder()
                .lectureSchedule(existingSchedule)
                .user(user)
                .build();
        applyScheduleRepo.save(existingApplySchedule);

        //when
        ApplyLectureSchedule successApply = service.apply(new LectureApplyCommand(user.getId(), requestSchedule.getId()));


        //db검증
        ApplyLectureSchedule findSchedule = applyScheduleRepo.findByLectureScheduleId(requestSchedule.getId()).get();
        //then
        assertThat(successApply.getLectureSchedule().getStudentCount()).isEqualTo(initialStudentCnt + 1);
        assertThat(findSchedule.getId()).isEqualTo(successApply.getId());
    }

    @Test
    @DirtiesContext
    public void 특강신청여부조회_통합테스트() {
        //given
        LocalDate givenDay = LocalDate.now();
        Lecture lecture1 = Lecture.builder()
                .title("미스터킴의 소름끼치는 자바 특강")
                .teacher("미스터킴")
                .build();
        Lecture lecture2 = Lecture.builder()
                .title("디자인 패턴이 궁금해? 내게 와")
                .teacher("패턴킹")
                .build();
        LectureSchedule schedule1 = LectureSchedule.builder()
                .id(1L)
                .lecture(lecture1)
                .maxStudent(30)
                .studentCount(29)
                .startTime(LocalDateTime.now().plusHours(1))
                .endTime(LocalDateTime.now().plusHours(2))
                .build();
        LectureSchedule schedule2 = LectureSchedule.builder()
                .id(2L)
                .maxStudent(30)
                .studentCount(28)
                .lecture(lecture2)
                .startTime(LocalDateTime.now().plusHours(1))
                .endTime(LocalDateTime.now().plusHours(2))
                .build();

        lectureRepo.save(lecture1);
        lectureRepo.save(lecture2);
        scheduleRepo.save(schedule1);
        scheduleRepo.save(schedule2);
        ///when
        List<LectureSchedule> getSchedules =  scheduleQueryService.getSchedulesByDate(new LectureQueryCommand(givenDay));

        assertThat(getSchedules).hasSize(2);
        assertThat(getSchedules.get(0).getId()).isEqualTo(schedule1.getId());
        assertThat(getSchedules.get(0).getStartTime()).isEqualTo(schedule1.getStartTime());
        assertThat(getSchedules.get(1).getId()).isEqualTo(schedule2.getId());
        assertThat(getSchedules.get(1).getStartTime()).isEqualTo(schedule2.getStartTime());
    }

    @Test
    @DirtiesContext
    void 신청완료한_특강_조회_성공() {
        // Given
        User user = userRepository.save(new User(1 ,"기만석"));

        Lecture lecture = Lecture.builder()
                .title("미스터킴의 소름끼치는 자바 특강")
                .teacher("미스터킴")
                .build();
        LectureSchedule schedule = LectureSchedule.builder()
                        .lecture(lecture)
                        .startTime(LocalDateTime.now())
                        .endTime(LocalDateTime.now().plusHours(1))
                        .build();

        lectureRepo.save(lecture);
        scheduleRepo.save(schedule);
        applyScheduleRepo.save(new ApplyLectureSchedule(user, schedule));

        // When
        List<ApplyLectureSchedule> result = scheduleQueryService.getCompletedLectures(user.getId());

        // Then
        ApplyLectureSchedule applyLecture = result.get(0);
        assertThat(applyLecture.getLectureSchedule().getId()).isEqualTo(schedule.getId());
        assertThat(applyLecture.getLectureSchedule().getLecture().getTitle()).isEqualTo("미스터킴의 소름끼치는 자바 특강");
        assertThat(applyLecture.getLectureSchedule().getLecture().getTeacher()).isEqualTo("미스터킴");
    }
}
