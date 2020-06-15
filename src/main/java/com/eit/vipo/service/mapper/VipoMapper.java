package com.eit.vipo.service.mapper;

import com.eit.vipo.domain.*;
import com.eit.vipo.service.dto.VipoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Vipo} and its DTO {@link VipoDTO}.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface VipoMapper extends EntityMapper<VipoDTO, Vipo> {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.login", target = "userLogin")
    VipoDTO toDto(Vipo vipo);

    @Mapping(source = "userId", target = "user")
    @Mapping(target = "entries", ignore = true)
    @Mapping(target = "removeEntries", ignore = true)
    @Mapping(target = "store", ignore = true)
    Vipo toEntity(VipoDTO vipoDTO);

    default Vipo fromId(Long id) {
        if (id == null) {
            return null;
        }
        Vipo vipo = new Vipo();
        vipo.setId(id);
        return vipo;
    }
}
