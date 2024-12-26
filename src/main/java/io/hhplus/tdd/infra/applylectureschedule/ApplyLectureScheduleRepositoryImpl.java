package io.hhplus.tdd.infra.applylectureschedule;

import io.hhplus.tdd.domain.applylectureschedule.ApplyLectureSchedule;
import io.hhplus.tdd.domain.applylectureschedule.IApplyLectureScheduleRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ApplyLectureScheduleRepositoryImpl implements IApplyLectureScheduleRepository {

    private final JpaApplyLectureSchedule jpaApplyLectureSchedule;
    public ApplyLectureScheduleRepositoryImpl(JpaApplyLectureSchedule jpaApplyLectureSchedule) {
        this.jpaApplyLectureSchedule = jpaApplyLectureSchedule;
    }
    @Override
    public ApplyLectureSchedule save(ApplyLectureSchedule applyLectureSchedule) {
        return jpaApplyLectureSchedule.save(applyLectureSchedule);
    }

    @Override
    public List<ApplyLectureSchedule> findAllByUserId(long userId) {
        return jpaApplyLectureSchedule.findAllByUserId(userId);
    }

    @Override
    public Optional<ApplyLectureSchedule> findByLectureScheduleId(long scheduleId) {
        return jpaApplyLectureSchedule.findByLectureScheduleId(scheduleId);
    }

    @Override
    public void deleteAll() {
        jpaApplyLectureSchedule.deleteAll();
    }

    @Override
    public List<ApplyLectureSchedule> findAll() {
        return jpaApplyLectureSchedule.findAll();
    }

}
