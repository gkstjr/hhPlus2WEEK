package io.hhplus.tdd.domain.applylectureschedule;

import io.hhplus.tdd.domain.User.IUserRepository;
import io.hhplus.tdd.domain.User.User;
import io.hhplus.tdd.domain.lectureschedule.ILectureScheduleRepository;
import io.hhplus.tdd.domain.lectureschedule.LectureSchedule;
import io.hhplus.tdd.domain.applylectureschedule.dto.LectureApplyCommand;
import org.springframework.stereotype.Service;

import java.util.List;

/**특강 신청 가능 도메인 서비스
 * (개인적 목표)
 * 서비스의 역할과 책임을 명확히 하고 , 도메인 객체의 비즈니스 로직은 객체 내부에 집둥되게끔 고민하고 구현해보자.
 * (의문점)
 * 해당 도메인 서비스가 맡은 책임인 "특강 신청" 행위가 명확하다면 여러개의 도메인 객체 , infra(Repository)를 사용해도 무방한가?
 */
 @Service
public class ApplyLectureScheduleService {

    private final ILectureScheduleRepository iLectureScheduleRepository;
    private final IApplyLectureScheduleRepository iApplyLectureScheduleRepository;
    private final IUserRepository iUserRepository;
    public ApplyLectureScheduleService(ILectureScheduleRepository iLectureScheduleRepository, IApplyLectureScheduleRepository iApplyLectureScheduleRepository, IUserRepository iUserRepository) {
        this.iLectureScheduleRepository = iLectureScheduleRepository;
        this.iApplyLectureScheduleRepository = iApplyLectureScheduleRepository;
        this.iUserRepository = iUserRepository;
    }

    //특강 신청
    public ApplyLectureSchedule apply(LectureApplyCommand command) {
        LectureSchedule findLectureSchedule = iLectureScheduleRepository.findById(command.lectureScheduleId())
                .orElseThrow(() -> new IllegalArgumentException("신청하신 특강이 없습니다. lectureId : " + command.lectureScheduleId()));
        //TODO 유저 리퍼지토리로 가져오기(기존 애플리케이션이라면 security로 처리
        User user = iUserRepository.findById(command.userId())
                .orElseThrow(() -> new IllegalArgumentException("신청한 사용자 Id가 없습니다."));

        List<ApplyLectureSchedule> userApplyLectureSchedule = iApplyLectureScheduleRepository.findAllByUserId(command.userId());

        findLectureSchedule.validateDuplicateLecture(userApplyLectureSchedule); //동일한 신청자(userId)가 동일한 강의를 신청(회차별이 아닌 특강종류로 구분)했으면 예외 처리
        findLectureSchedule.validateDuplicateTime(userApplyLectureSchedule); //신청자가 이미 같은 날짜 시간에 다른 특강 신청이 되어 있으면 예외 처리
        findLectureSchedule.apply(); //조회된 특강 초과 인원 체크 및 추가
        ApplyLectureSchedule applyLectureSchedule = new ApplyLectureSchedule(user ,findLectureSchedule);

        return iApplyLectureScheduleRepository.save(applyLectureSchedule);
    }
}
