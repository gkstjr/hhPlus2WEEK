package io.hhplus.tdd.service;

import org.springframework.stereotype.Service;

@Service
public class LectureFacade {
    private final LectureApplicationService applicationService;
    private final LectureQueryService queryService;

    public LectureFacade(LectureApplicationService applicationService , LectureQueryService queryService) {
        this.applicationService = applicationService;
        this.queryService = queryService;
    }


}
