package io.hhplus.tdd.infra;

import io.hhplus.tdd.domain.ApplyLecture;
import io.hhplus.tdd.domain.Lecture.ILectureRepository;

public class LectureRepositoryImpl implements ILectureRepository {
    //여러 개의 repository 의존 가능(ex. jpa , mybatis , queryDsl_

    @Override
    public ApplyLecture save(Long id) {
        return new ApplyLecture();
    }
}
