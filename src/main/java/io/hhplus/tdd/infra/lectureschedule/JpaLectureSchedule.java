package io.hhplus.tdd.infra.lectureschedule;

import io.hhplus.tdd.domain.applylectureschedule.ApplyLectureSchedule;
import io.hhplus.tdd.domain.lectureschedule.LectureSchedule;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface JpaLectureSchedule extends JpaRepository<LectureSchedule,Long> {
    @Query("SELECT ls FROM LectureSchedule ls WHERE DATE(ls.startTime) = :date")
    List<LectureSchedule> findAllByDate(LocalDate date);


    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT ls FROM LectureSchedule ls WHERE ls.id = :id")
    Optional<LectureSchedule> findByIdWithLock(long id);
}
