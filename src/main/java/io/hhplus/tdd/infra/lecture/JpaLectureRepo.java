package io.hhplus.tdd.infra.lecture;

import io.hhplus.tdd.domain.Lecture.Lecture;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaLectureRepo extends JpaRepository<Lecture , Long> {
}
