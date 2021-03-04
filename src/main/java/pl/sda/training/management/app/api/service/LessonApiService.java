package pl.sda.training.management.app.api.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.CollectionModel;
import org.springframework.stereotype.Service;
import pl.sda.training.management.app.api.controller.LessonApiController;
import pl.sda.training.management.app.api.dto.LessonResource;
import pl.sda.training.management.app.domain.model.Lesson;
import pl.sda.training.management.app.domain.service.LessonService;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static pl.sda.training.management.app.api.dto.LessonResource.LESSON_ASSEMBLER;

@Service
@RequiredArgsConstructor
public class LessonApiService {
    private final LessonService lessonService;

    private final PagedResourcesAssembler<Lesson> pagedResourcesAssembler;

    public CollectionModel<LessonResource> getLessons(Pageable pageable) {
        Page<Lesson> lessonsPage = lessonService.getPage(pageable);
        return pagedResourcesAssembler
                .toModel(lessonsPage, LESSON_ASSEMBLER)
                .add(linkTo(LessonApiController.class)
                        .withRel("lessons"));
    }

    public LessonResource getLesson(Long id) {
        return LESSON_ASSEMBLER.toModel(lessonService.getById(id));
    }
}
