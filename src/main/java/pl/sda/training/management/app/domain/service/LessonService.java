package pl.sda.training.management.app.domain.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.sda.training.management.app.domain.model.Lesson;
import pl.sda.training.management.app.domain.repository.LessonRepo;
import pl.sda.training.management.app.exception.LessonNotFoundException;

@Service
@RequiredArgsConstructor
public class LessonService {

    private final LessonRepo lessonRepo;

    public Page<Lesson> getPage(Pageable pageable) {
        return lessonRepo.findAll(pageable);
    }

    public Lesson getById(Long id) {
        return lessonRepo.findById(id)
                .orElseThrow(() -> new LessonNotFoundException("Lesson with id: " + id + " not found."));
    }
}
