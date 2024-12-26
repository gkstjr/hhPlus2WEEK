package io.hhplus.tdd.infra.applylecture;

import io.hhplus.tdd.domain.applylectureschedule.ApplyLectureSchedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface JpaApplyLectureSchedule extends JpaRepository<ApplyLectureSchedule,Long> {
    // 사용자 ID로 신청 내역 조회
    List<ApplyLectureSchedule> findAllByUserId(long userId);

    Optional<ApplyLectureSchedule> findByLectureScheduleId(long scheduleId);
}
