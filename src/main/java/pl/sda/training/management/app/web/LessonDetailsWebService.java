package pl.sda.training.management.app.web;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.sda.training.management.app.domain.model.EditionCode;
import pl.sda.training.management.app.domain.model.LessonDetails;
import pl.sda.training.management.app.domain.model.Login;
import pl.sda.training.management.app.domain.service.LessonDetailsService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LessonDetailsWebService {
    private final LessonDetailsService lessonDetailsService;

    public List<LessonDetailsToShow> getAllByTrainerLoginAndLessonBlockId(String trainerLogin, Long blockId) {
        return lessonDetailsToShowListOf(
                lessonDetailsService.getAllByTrainerLoginAndLessonBlockId(Login.of(trainerLogin), blockId));
    }

    public List<LessonDetailsToShow> getAllByTrainerLogin(String trainerLogin) {
        return lessonDetailsToShowListOf(
                lessonDetailsService.getAllByTrainerLogin(Login.of(trainerLogin)));
    }

    private List<LessonDetailsToShow> lessonDetailsToShowListOf(List<LessonDetails> lessonDetailsList) {
        return lessonDetailsList
                .stream()
                .map(LessonDetailsToShow::of)
                .collect(Collectors.toList());
    }

    public List<LessonDetailsToShow> getAllByEditionCodeAndBlockId(String editionCode, Long blockId) {
        return lessonDetailsToShowListOf(
                lessonDetailsService.getAllByEditionCodeAndBlockId(EditionCode.of(editionCode), blockId));
    }
}
