package io.hhplus.tdd.infra.lecture;

import io.hhplus.tdd.domain.Lecture.Lecture;
import io.hhplus.tdd.domain.applylecture.ApplyLecture;
import io.hhplus.tdd.domain.Lecture.ILectureRepository;

import java.util.Optional;

public class LectureRepositoryImpl implements ILectureRepository {
    //여러 개의 repository 의존 가능(ex. jpa , mybatis , queryDsl_


    @Override
    public Optional<Lecture> findById(long lectureId) {
        return Optional.empty();
    }
}
