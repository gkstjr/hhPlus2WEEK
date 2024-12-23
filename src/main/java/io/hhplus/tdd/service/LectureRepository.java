package io.hhplus.tdd.service;

import io.hhplus.tdd.domain.ApplyLecture;
import org.springframework.stereotype.Repository;

import java.util.Objects;

@Repository
public interface LectureRepository {
    ApplyLecture save(Long id);
}
