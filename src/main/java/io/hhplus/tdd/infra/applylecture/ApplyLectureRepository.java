package io.hhplus.tdd.infra.applylecture;

import io.hhplus.tdd.domain.applylecture.ApplyLecture;
import io.hhplus.tdd.domain.applylecture.IApplyLectureRepository;

import java.util.List;
import java.util.Optional;

public class ApplyLectureRepository implements IApplyLectureRepository {

    @Override
    public Optional<ApplyLecture> save(Long id) {
        return Optional.empty();
    }
    @Override
    public boolean existsByUserIdAndLectureId(long userId, long lectureId) {
        return true;
    }

    @Override
    public List<ApplyLecture> findByUserId(long userId) {
        return null;
    }
}
