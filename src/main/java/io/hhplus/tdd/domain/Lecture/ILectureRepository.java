package io.hhplus.tdd.domain.Lecture;

import io.hhplus.tdd.domain.ApplyLecture;
import org.springframework.stereotype.Repository;

@Repository
public interface ILectureRepository {
    ApplyLecture save(Long id);
}
