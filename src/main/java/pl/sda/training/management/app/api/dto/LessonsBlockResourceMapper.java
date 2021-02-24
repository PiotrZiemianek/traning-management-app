package pl.sda.training.management.app.api.dto;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import pl.sda.training.management.app.domain.model.BlockName;
import pl.sda.training.management.app.domain.model.LessonsBlock;

@Mapper(uses = LessonResourceMapper.class)
public interface LessonsBlockResourceMapper {

    LessonsBlockResourceMapper INSTANCE = Mappers.getMapper(LessonsBlockResourceMapper.class);

    LessonsBlockResource lessonsBlockToDto(LessonsBlock entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "course", ignore = true)
    LessonsBlock resourceToLessonsBlock(LessonsBlockResource dto);

    @Mapping(target = "blockName", source = "value")
    BlockName stringToBlockName(String value);

    default String blockNameToString(BlockName blockName) {
        return blockName == null ? null : blockName.value();
    }
}
