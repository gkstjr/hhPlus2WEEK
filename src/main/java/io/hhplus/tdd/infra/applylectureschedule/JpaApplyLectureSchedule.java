package io.hhplus.tdd.infra.applylectureschedule;

import io.hhplus.tdd.domain.applylectureschedule.ApplyLectureSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface JpaApplyLectureSchedule extends JpaRepository<ApplyLectureSchedule,Long> {
    @Query("SELECT als FROM ApplyLectureSchedule als " +
            "JOIN FETCH als.lectureSchedule ls " +
            "JOIN FETCH ls.lecture l " +
            "WHERE als.user.id = :userId")
    List<ApplyLectureSchedule> findAllByUserId(long userId);

    Optional<ApplyLectureSchedule> findByLectureScheduleId(long scheduleId);
}
