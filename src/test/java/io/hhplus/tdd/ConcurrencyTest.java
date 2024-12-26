package io.hhplus.tdd;

import io.hhplus.tdd.domain.Lecture.ILectureRepository;
import io.hhplus.tdd.domain.Lecture.Lecture;
import io.hhplus.tdd.domain.User.IUserRepository;
import io.hhplus.tdd.domain.User.User;
import io.hhplus.tdd.domain.applylectureschedule.ApplyLectureSchedule;
import io.hhplus.tdd.domain.applylectureschedule.ApplyLectureScheduleService;
import io.hhplus.tdd.domain.applylectureschedule.IApplyLectureScheduleRepository;
import io.hhplus.tdd.domain.applylectureschedule.dto.LectureApplyCommand;
import io.hhplus.tdd.domain.lectureschedule.ILectureScheduleRepository;
import io.hhplus.tdd.domain.lectureschedule.LectureSchedule;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import static org.assertj.core.api.Assertions.*;

@SpringBootTest
public class ConcurrencyTest {

    @Autowired
    private ApplyLectureScheduleService applyLectureScheduleService;

    @Autowired
    private IApplyLectureScheduleRepository applyLectureScheduleRepository;

    @Autowired
    private ILectureScheduleRepository lectureScheduleRepository;
    @Autowired
    private ILectureRepository iLectureRepository;
    @Autowired
    private IUserRepository userRepository;
    @Test
    void 동시에_40명이_신청시_30명만_성공_동시성테스트() throws InterruptedException {
        // Given
        Lecture requestLecture = Lecture.builder()
                .title("미스터킴의 소름끼치는 자바 특강")
                .teacher("미스터킴")
                .build();
        LectureSchedule lectureSchedule = LectureSchedule.builder()
                .lecture(requestLecture)
                .maxStudent(30)
                .studentCount(0)
                .build();
        int threads = 40;

        for(int i = 0 ; i < threads; i++) {
            userRepository.save(User.builder()
                            .name("김한석" + i)
                            .build());
        }
        iLectureRepository.save(requestLecture);
        lectureScheduleRepository.save(lectureSchedule);

        long lectureScheduleId = lectureSchedule.getId();
        long originUserId = 1L;

        ExecutorService executorService = Executors.newFixedThreadPool(threads);
        CountDownLatch latch = new CountDownLatch(threads);

        //when
        for(int i = 0; i < threads; i++) {
            long currentUserId = originUserId + i;
            executorService.execute(() -> {
                try {
                    applyLectureScheduleService.apply(new LectureApplyCommand(currentUserId, lectureScheduleId));
//                    System.out.println("디버깅 확인 : " + lectureSchedule.getStudentCount());
                } catch (Exception e) {
//                    System.out.println("예외 발생 : " + e.getMessage());
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await();

        // Then
        List<ApplyLectureSchedule> successApplyList = applyLectureScheduleRepository.findAll();
        LectureSchedule successLectureSchedule = lectureScheduleRepository.findById(lectureScheduleId).orElseThrow();

        assertThat(successApplyList.size()).isEqualTo(30);
        assertThat(successLectureSchedule.getStudentCount()).isEqualTo(30);
    }

    @Test
    void 동일한_유저가_동일한_특강_5번_신청시_1번만_성공_동시성테스트() throws InterruptedException {
        // Given
        Lecture requestLecture = Lecture.builder()
                .title("미스터킴의 소름끼치는 자바 특강")
                .teacher("미스터킴")
                .build();
        iLectureRepository.save(requestLecture);
        User user = User.builder()
                .name("기만석")
                .build();
        userRepository.save(user);

        LectureSchedule lectureSchedule = LectureSchedule.builder()
                .lecture(requestLecture)
                .maxStudent(30)
                .studentCount(0)
                .build();
        lectureScheduleRepository.save(lectureSchedule);

        long scheduleId = lectureSchedule.getId();
        long userId = user.getId();

        int threads = 5; // 동일 유저의 신청 시도 횟수
        ExecutorService executorService = Executors.newFixedThreadPool(threads);
        CountDownLatch latch = new CountDownLatch(threads);

        // When
        for (int i = 0; i < threads; i++) {
            executorService.execute(() -> {
                try {
                    applyLectureScheduleService.apply(new LectureApplyCommand(userId, scheduleId));
                } catch (Exception e) {
                    System.out.println("중복예외 발생 = " + e.getMessage());
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await();

        // Then
        List<ApplyLectureSchedule> appliyList = applyLectureScheduleRepository.findAll();

        assertThat(appliyList.size()).isEqualTo(1);
        assertThat(appliyList.get(0).getUser().getId()).isEqualTo(userId);
        assertThat(appliyList.get(0).getLectureSchedule().getId()).isEqualTo(scheduleId);
    }
}
