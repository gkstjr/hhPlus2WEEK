package io.hhplus.tdd.domain.lectureschedule;

import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ILectureScheduleRepository {

    Optional<LectureSchedule> findById(long id);

    LectureSchedule save(LectureSchedule lectureSchedule);

    List<LectureSchedule> findAllByDate(LocalDate date);
}
