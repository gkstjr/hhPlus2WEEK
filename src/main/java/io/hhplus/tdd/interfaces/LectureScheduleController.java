package io.hhplus.tdd.interfaces;

import io.hhplus.tdd.domain.applylectureschedule.ApplyLectureSchedule;
import io.hhplus.tdd.domain.lectureschedule.LectureSchedule;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/lectures")
public class LectureScheduleController {

    //특강 신청 API(특정 강의에 대해)
    @PostMapping("/{lectureId}/apply")
    public ApplyLectureSchedule apply(long lectureId) {

        return null;
    }
    //특강 신청 가능 여부 조회("날짜별"을 어떻게 정의할까?)
    @GetMapping("/available")
    public List<LectureSchedule> getAvailableLectures() {

        return null;
    }

    //특강 신청 완료 목록 조회
    @GetMapping("/success/{userId}") // userId는 차라리 파라미터로 받는 게 restful 규약에 적법
    public List<ApplyLectureSchedule> getSuccessLectures(long userId) {

        return null;
    }

}
