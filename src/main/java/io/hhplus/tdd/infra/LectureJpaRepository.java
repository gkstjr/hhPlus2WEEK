package io.hhplus.tdd.infra;

import io.hhplus.tdd.domain.ApplyLecture;
import io.hhplus.tdd.service.LectureRepository;

public class LectureJpaRepository implements LectureRepository {


    @Override
    public ApplyLecture save(Long id) {
        return new ApplyLecture();

}
