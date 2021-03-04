package pl.sda.training.management.app.api.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.CollectionModel;
import org.springframework.stereotype.Service;
import pl.sda.training.management.app.api.controller.LessonsBlockApiController;
import pl.sda.training.management.app.api.dto.LessonsBlockResource;
import pl.sda.training.management.app.domain.model.LessonsBlock;
import pl.sda.training.management.app.domain.service.LessonsBlockService;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static pl.sda.training.management.app.api.dto.LessonsBlockResource.LESSONS_BLOCK_ASSEMBLER;

@Service
@RequiredArgsConstructor
public class LessonsBlockApiService {

    private final LessonsBlockService lessonsBlockService;

    private final PagedResourcesAssembler<LessonsBlock> pagedResourcesAssembler;

    public CollectionModel<LessonsBlockResource> getBlocks(Pageable pageable) {

        Page<LessonsBlock> blocksPage = lessonsBlockService.getPage(pageable);

        return pagedResourcesAssembler
                .toModel(blocksPage, LESSONS_BLOCK_ASSEMBLER)
                .add(linkTo(LessonsBlockApiController.class)
                        .withRel("lessons-blocks"));
    }

    public LessonsBlockResource getLessonBlock(Long id) {
        return LESSONS_BLOCK_ASSEMBLER.toModel(lessonsBlockService.getById(id));
    }
}
