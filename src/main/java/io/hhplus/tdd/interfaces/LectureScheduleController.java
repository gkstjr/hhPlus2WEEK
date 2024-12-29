package io.hhplus.tdd.interfaces;

import io.hhplus.tdd.domain.applylectureschedule.ApplyLectureSchedule;
import io.hhplus.tdd.domain.applylectureschedule.ApplyLectureScheduleService;
import io.hhplus.tdd.domain.applylectureschedule.dto.LectureApplyCommand;
import io.hhplus.tdd.domain.lectureschedule.LectureSchedule;
import io.hhplus.tdd.domain.lectureschedule.LectureScheduleQueryService;
import io.hhplus.tdd.domain.lectureschedule.dto.LectureQueryCommand;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/lectures")
public class LectureScheduleController {
    private final LectureScheduleQueryService queryService;
    private final ApplyLectureScheduleService applyService;

    public LectureScheduleController(LectureScheduleQueryService queryService, ApplyLectureScheduleService applyService) {
        this.queryService = queryService;
        this.applyService = applyService;
    }

    //특강 신청 API(특정 강의에 대해)
    @PostMapping("/{lectureId}/apply")
    public ApplyLectureSchedule apply(long lectureScheduleId , long userId) {
        return applyService.apply(new LectureApplyCommand(userId , lectureScheduleId));
    }
    //특강 신청 가능 여부 조회("날짜별"을 일단위로 정해서 특정 일의 목록 조회)
    @GetMapping("/available")
    public List<LectureSchedule> getAvailableLectures(LocalDate date) {
        return queryService.getSchedulesByDate(new LectureQueryCommand(date));
    }

    //특강 신청 완료 목록 조회
    @GetMapping("/success") // userId는 차라리 파라미터로 받는 게 restful 규약에 적법
    public List<ApplyLectureSchedule> getSuccessLectures(@RequestParam long userId) {
        return queryService.getCompletedLectures(userId);
    }

}
