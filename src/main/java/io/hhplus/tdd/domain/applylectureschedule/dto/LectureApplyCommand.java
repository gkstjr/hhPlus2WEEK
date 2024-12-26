package io.hhplus.tdd.domain.applylectureschedule.dto;

public record LectureApplyCommand(
        long userId,

        long lectureScheduleId
) {
}
