package pl.sda.training.management.app.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.hateoas.CollectionModel;
import org.springframework.web.bind.annotation.*;
import pl.sda.training.management.app.api.dto.LessonsBlockResource;
import pl.sda.training.management.app.api.service.LessonsBlockApiService;

import static pl.sda.training.management.app.utils.Constants.*;

@RestController
@RequestMapping(path = API_URL + "/lessons-blocks", produces = API_PRODUCES)
@CrossOrigin("*")
@RequiredArgsConstructor
public class LessonsBlockApiController {
    private final LessonsBlockApiService lessonsBlockApiService;

    @GetMapping
    public CollectionModel<LessonsBlockResource> getBlocks(
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size) {

        if (page == null) {
            page = 0;
        }

        if (size == null) {
            size = DEFAULT_API_PAGE_SIZE;
        }

        return lessonsBlockApiService.getBlocks(PageRequest.of(page, size));
    }

    @GetMapping("/{id}")
    public LessonsBlockResource getLessonsBlock(@PathVariable Long id){
        return lessonsBlockApiService.getLessonBlock(id);
    }
}
