package io.hhplus.tdd.infra.lectureschedule;

import io.hhplus.tdd.domain.applylectureschedule.ApplyLectureSchedule;
import io.hhplus.tdd.domain.lectureschedule.LectureSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface JpaLectureSchedule extends JpaRepository<LectureSchedule,Long> {
    @Query("SELECT ls FROM LectureSchedule ls WHERE DATE(ls.startTime) = :date")
    List<LectureSchedule> findAllByDate(LocalDate date);

}
