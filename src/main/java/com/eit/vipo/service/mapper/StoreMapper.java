package com.eit.vipo.service.mapper;

import com.eit.vipo.domain.*;
import com.eit.vipo.service.dto.StoreDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Store} and its DTO {@link StoreDTO}.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class, VipoMapper.class})
public interface StoreMapper extends EntityMapper<StoreDTO, Store> {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.login", target = "userLogin")
    @Mapping(source = "vipo.id", target = "vipoId")
    @Mapping(source = "vipo.name", target = "vipoName")
    StoreDTO toDto(Store store);

    @Mapping(source = "userId", target = "user")
    @Mapping(source = "vipoId", target = "vipo")
    Store toEntity(StoreDTO storeDTO);

    default Store fromId(Long id) {
        if (id == null) {
            return null;
        }
        Store store = new Store();
        store.setId(id);
        return store;
    }
}
