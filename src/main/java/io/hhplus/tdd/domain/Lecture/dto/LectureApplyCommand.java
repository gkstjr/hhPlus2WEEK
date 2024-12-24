package io.hhplus.tdd.domain.Lecture.dto;

public record LectureApplyCommand(
        long lectureId,
        long userId
) {
}
