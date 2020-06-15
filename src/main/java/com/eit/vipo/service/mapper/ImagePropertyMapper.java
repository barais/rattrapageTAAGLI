package com.eit.vipo.service.mapper;

import com.eit.vipo.domain.*;
import com.eit.vipo.service.dto.ImagePropertyDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ImageProperty} and its DTO {@link ImagePropertyDTO}.
 */
@Mapper(componentModel = "spring", uses = {VipoEntryMapper.class})
public interface ImagePropertyMapper extends EntityMapper<ImagePropertyDTO, ImageProperty> {

    @Mapping(source = "entry.id", target = "entryId")
    ImagePropertyDTO toDto(ImageProperty imageProperty);

    @Mapping(source = "entryId", target = "entry")
    ImageProperty toEntity(ImagePropertyDTO imagePropertyDTO);

    default ImageProperty fromId(Long id) {
        if (id == null) {
            return null;
        }
        ImageProperty imageProperty = new ImageProperty();
        imageProperty.setId(id);
        return imageProperty;
    }
}
