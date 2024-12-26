package io.hhplus.tdd.domain.applylectureschedule;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface IApplyLectureScheduleRepository {

    public ApplyLectureSchedule save(ApplyLectureSchedule applyLectureSchedule);


    List<ApplyLectureSchedule> findAllByUserId(long userId);

    Optional<ApplyLectureSchedule> findByLectureScheduleId(long ScheduleId);

    void deleteAll();
}
