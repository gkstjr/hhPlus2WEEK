package io.hhplus.tdd.domain.Lecture;

import io.hhplus.tdd.domain.applylecture.ApplyLecture;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ILectureRepository {

    Optional<Lecture> findById(long lectureId);
}
