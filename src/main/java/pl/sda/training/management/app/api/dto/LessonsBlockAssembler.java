package pl.sda.training.management.app.api.dto;

import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import pl.sda.training.management.app.api.controller.LessonsBlockApiController;
import pl.sda.training.management.app.domain.model.LessonsBlock;

public class LessonsBlockAssembler extends RepresentationModelAssemblerSupport<LessonsBlock, LessonsBlockResource> {
    public LessonsBlockAssembler() {
        super(LessonsBlockApiController.class, LessonsBlockResource.class);
    }


    @Override
    public LessonsBlockResource toModel(LessonsBlock entity) {
        return createModelWithId(entity.getId(),entity);
    }

    @Override
    protected LessonsBlockResource instantiateModel(LessonsBlock entity) {
        return LessonsBlockResourceMapper.INSTANCE.lessonsBlockToDto(entity);
    }
}
