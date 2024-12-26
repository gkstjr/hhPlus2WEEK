package io.hhplus.tdd.infra.lectureschedule;

import io.hhplus.tdd.domain.lectureschedule.LectureSchedule;
import io.hhplus.tdd.domain.lectureschedule.ILectureScheduleRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
@Repository
public class LectureScheduleRepositoryImpl implements ILectureScheduleRepository {
    //여러 개의 repository 의존 가능(ex. jpa , mybatis , queryDsl_
    private final JpaLectureSchedule jpaLectureSchedule;

    public LectureScheduleRepositoryImpl(JpaLectureSchedule jpaLectureSchedule) {
        this.jpaLectureSchedule = jpaLectureSchedule;
    }
    @Override
    public Optional<LectureSchedule> findById(long id) {
        return jpaLectureSchedule.findById(id);
    }

    @Override
    public LectureSchedule save(LectureSchedule lectureSchedule) {
        return jpaLectureSchedule.save(lectureSchedule);
    }

    @Override
    public List<LectureSchedule> findAllByDate(LocalDate date) {
        return jpaLectureSchedule.findAllByDate(date);
    }

    @Override
    public void deleteAll() {
        jpaLectureSchedule.deleteAll();
    }
}
