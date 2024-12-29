package io.hhplus.tdd.domain.lectureschedule.dto;

import java.time.LocalDate;

public record LectureQueryCommand(
        LocalDate date //특정 일
) {
}
