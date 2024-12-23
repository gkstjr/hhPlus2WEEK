package io.hhplus.tdd.service;

import org.springframework.stereotype.Service;

@Service
public class LectureApplicationService {

    private final LectureRepository lectureRepository;

    public LectureApplicationService(LectureRepository repository) {
        this.lectureRepository = repository;
    }

    //특강 신청

}
