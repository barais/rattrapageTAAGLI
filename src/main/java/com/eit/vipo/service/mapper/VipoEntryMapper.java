package com.eit.vipo.service.mapper;

import com.eit.vipo.domain.*;
import com.eit.vipo.service.dto.VipoEntryDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link VipoEntry} and its DTO {@link VipoEntryDTO}.
 */
@Mapper(componentModel = "spring", uses = {VipoMapper.class})
public interface VipoEntryMapper extends EntityMapper<VipoEntryDTO, VipoEntry> {

    @Mapping(source = "vipo.id", target = "vipoId")
    VipoEntryDTO toDto(VipoEntry vipoEntry);

    @Mapping(target = "props", ignore = true)
    @Mapping(target = "removeProps", ignore = true)
    @Mapping(source = "vipoId", target = "vipo")
    VipoEntry toEntity(VipoEntryDTO vipoEntryDTO);

    default VipoEntry fromId(Long id) {
        if (id == null) {
            return null;
        }
        VipoEntry vipoEntry = new VipoEntry();
        vipoEntry.setId(id);
        return vipoEntry;
    }
}
