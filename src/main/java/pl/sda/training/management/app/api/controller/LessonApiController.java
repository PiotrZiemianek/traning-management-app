package pl.sda.training.management.app.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.hateoas.CollectionModel;
import org.springframework.web.bind.annotation.*;
import pl.sda.training.management.app.api.dto.LessonResource;
import pl.sda.training.management.app.api.service.LessonApiService;

import static pl.sda.training.management.app.utils.Constants.*;

@RestController
@RequestMapping(path = API_URL + "/lessons", produces = API_PRODUCES)
@CrossOrigin("*")
@RequiredArgsConstructor
public class LessonApiController {
    private final LessonApiService lessonApiService;

    @GetMapping
    public CollectionModel<LessonResource> getLessons(
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size) {

        if (page == null) {
            page = 0;
        }

        if (size == null) {
            size = DEFAULT_API_PAGE_SIZE;
        }

        return lessonApiService.getLessons(PageRequest.of(page, size));
    }

    @GetMapping("/{id}")
    public LessonResource getLesson(@PathVariable Long id) {
        return lessonApiService.getLesson(id);
    }
}
