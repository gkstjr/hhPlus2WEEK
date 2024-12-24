package io.hhplus.tdd.interfaces;

import io.hhplus.tdd.domain.applylecture.ApplyLecture;
import io.hhplus.tdd.domain.Lecture.Lecture;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/lectures")
public class LectureController {

    //특강 신청 API(특정 강의에 대해)
    @PostMapping("/{lectureId}/apply")
    public ApplyLecture apply(long lectureId) {

        return null;
    }
    //특강 신청 가능 여부 조회("날짜별"을 어떻게 정의할까?)
    @GetMapping("/available")
    public List<Lecture> getAvailableLectures() {

        return null;
    }

    //특강 신청 완료 목록 조회
    @GetMapping("/success/{userId}")
    public List<ApplyLecture> getSuccessLectures(long userId) {

        return null;
    }

}
