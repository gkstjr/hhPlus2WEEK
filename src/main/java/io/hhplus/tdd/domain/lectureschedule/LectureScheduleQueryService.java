package io.hhplus.tdd.domain.lectureschedule;

import io.hhplus.tdd.domain.lectureschedule.dto.LectureQueryCommand;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LectureScheduleQueryService {

    private final ILectureScheduleRepository lectureScheduleRepository;

    public LectureScheduleQueryService(ILectureScheduleRepository lectureScheduleRepository) {
        this.lectureScheduleRepository = lectureScheduleRepository;
    }

    //특정 날짜(일)의 신청 가능한 특강 조회
    public List<LectureSchedule> getSchedulesByDate(LectureQueryCommand command) {

        List<LectureSchedule> getSchedules = lectureScheduleRepository.findAllByDate(command.date());

        //입력날짜 특강 중 현재 시간(now) 이전에 시작한 특강 or 수강인원 초과 강의 제외
        getSchedules = getSchedules.stream()
                            .filter(schedule -> schedule.isAfter(LocalDateTime.now()))
                            .filter(LectureSchedule::isApplyPossible)
                            .collect(Collectors.toList());

        return getSchedules;
    }


}
