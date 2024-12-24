package io.hhplus.tdd.domain.applylecture;

import java.util.List;
import java.util.Optional;

public interface IApplyLectureRepository {

    public Optional<ApplyLecture> save(Long id);

    boolean existsByUserIdAndLectureId(long userId, long lectureId);

    List<ApplyLecture> findByUserId(long userId);
}
