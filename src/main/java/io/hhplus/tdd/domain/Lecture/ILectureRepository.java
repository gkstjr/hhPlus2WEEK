package io.hhplus.tdd.domain.Lecture;

public interface ILectureRepository {
    Lecture save(Lecture requestLecture);

    void deleteAll();
}
