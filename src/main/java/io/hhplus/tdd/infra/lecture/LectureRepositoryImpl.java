package io.hhplus.tdd.infra.lecture;

import io.hhplus.tdd.domain.Lecture.ILectureRepository;
import io.hhplus.tdd.domain.Lecture.Lecture;
import org.springframework.stereotype.Repository;

@Repository
public class LectureRepositoryImpl implements ILectureRepository {

    private final JpaLectureRepo jpaLectureRepo;


    public LectureRepositoryImpl(JpaLectureRepo jpaLectureRepo) {
        this.jpaLectureRepo = jpaLectureRepo;
    }

    @Override
    public Lecture save(Lecture lecture) {
        return jpaLectureRepo.save(lecture);
    }
}
