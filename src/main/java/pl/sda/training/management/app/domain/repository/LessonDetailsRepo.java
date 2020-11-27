package pl.sda.training.management.app.domain.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.sda.training.management.app.domain.model.LessonDetails;
import pl.sda.training.management.app.domain.model.Login;

import java.util.List;

@Repository
public interface LessonDetailsRepo extends JpaRepository<LessonDetails, Long> {

    List<LessonDetails> getAllByTrainer_User_LoginAndLesson_LessonsBlock_Id(Login trainerLogin, Long lessonsBlockId, Sort sort);

    List<LessonDetails> getAllByTrainer_User_Login(Login trainerLogin, Sort sort);
}
