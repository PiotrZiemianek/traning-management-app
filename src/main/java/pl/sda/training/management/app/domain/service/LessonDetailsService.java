package pl.sda.training.management.app.domain.service;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import pl.sda.training.management.app.domain.model.LessonDetails;
import pl.sda.training.management.app.domain.model.Login;
import pl.sda.training.management.app.domain.repository.LessonDetailsRepo;

import java.util.List;

@Service
@AllArgsConstructor
public class LessonDetailsService {
    private final LessonDetailsRepo lessonDetailsRepo;

    public List<LessonDetails> getAllByTrainerLoginAndLessonBlockId(Login trainerLogin, Long blockId) {
        return lessonDetailsRepo.getAllByTrainer_User_LoginAndLesson_LessonsBlock_Id(trainerLogin, blockId,
                Sort.by(Sort.Direction.DESC, "localDateTime"));
    }

    public List<LessonDetails> getAllByTrainerLogin(Login trainerLogin) {
        return lessonDetailsRepo.getAllByTrainer_User_Login(trainerLogin,
                Sort.by(Sort.Direction.DESC, "localDateTime"));
    }
}
