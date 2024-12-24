package io.hhplus.tdd.domain;

import io.hhplus.tdd.domain.Lecture.ILectureRepository;
import io.hhplus.tdd.domain.Lecture.Lecture;
import io.hhplus.tdd.domain.applylecture.ApplyLecture;
import io.hhplus.tdd.domain.Lecture.dto.LectureApplyCommand;
import io.hhplus.tdd.domain.applylecture.IApplyLectureRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**특강 신청 가능 도메인 서비스
 * (개인적 목표)
 * 서비스의 역할과 책임을 명확히 하고 , 도메인 객체의 비즈니스 로직은 객체 내부에 집둥되게끔 고민하고 구현해보자.
 * (의문점)
 * 해당 도메인 서비스가 맡은 책임인 "특강 신청" 행위가 명확하다면 여러개의 도메인 객체 , infra(Repository)를 사용해도 무방한가?
 */
 @Service
public class LectureApplicationService {

    private final ILectureRepository iLectureRepository;
    private final IApplyLectureRepository iApplyLectureRepository;

    public LectureApplicationService(ILectureRepository iLectureRepository , IApplyLectureRepository iApplyLectureRepository) {
        this.iLectureRepository = iLectureRepository;
        this.iApplyLectureRepository = iApplyLectureRepository;
    }

    //특강 신청
    public ApplyLecture apply(LectureApplyCommand command) {
        //LectureId 특강 조회(조회 없을 때 예외)
        Lecture foundLecture = iLectureRepository.findById(command.lectureId())
                .orElseThrow(() -> new IllegalArgumentException("신청하신 특강이 없습니다. lectureId : " + command.lectureId()));
        /**리뷰포인트
            동일한 강의 신청 검증 + 같은 날짜에 다른 특강 신청 검증
          - JPA 성능 생각해서 User객체와 Lecture객체 한 번에 같이 가져올까?/
          - 각각 Repository 에서 조건에 맞는 쿼리로 호출 해오는 게 더 효율적이다라고 판단.
          - 이유는 applyLecture 리스트를 전부 가져오면 필요 없는 데이터도 메모리에 로드 되기 때문에 비효율적
         */
        //동일한 신청자(userId)가 동일한 강의를 신청했으면 예외 처리(q & a 후 구현)
        if(iApplyLectureRepository.existsByUserIdAndLectureId(command.userId(), command.lectureId())) {
            throw new IllegalArgumentException("동일한 신청자가 동일한 강의를 신청할 수 없습니다.");
        }

        //신청자가 이미 같은 날짜 시간에 다른 특강 신청이 되어 있으면 예외 처리

        //조회된 특강 초과 인원 체크 및 신청(도메인)
        return null;
    }
}
