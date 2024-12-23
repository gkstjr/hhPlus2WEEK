package io.hhplus.tdd.domain.Lecture;

import io.hhplus.tdd.domain.Lecture.ILectureRepository;
import org.springframework.stereotype.Service;

@Service
public class LectureApplicationService {

    private final io.hhplus.tdd.domain.Lecture.ILectureRepository ILectureRepository;

    public LectureApplicationService(ILectureRepository repository) {
        this.ILectureRepository = repository;
    }

    //특강 신청

}
