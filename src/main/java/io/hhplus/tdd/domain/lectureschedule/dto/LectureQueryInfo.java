package io.hhplus.tdd.domain.lectureschedule.dto;

public record LectureQueryInfo(
    long scheduleId,
    String title,
    String teacher
) {
}
