package com.eit.vipo.service.impl;

import com.eit.vipo.service.StoreService;
import com.eit.vipo.domain.Store;
import com.eit.vipo.repository.StoreRepository;
import com.eit.vipo.repository.UserRepository;
import com.eit.vipo.security.AuthoritiesConstants;
import com.eit.vipo.security.SecurityUtils;
import com.eit.vipo.service.dto.StoreDTO;
import com.eit.vipo.service.mapper.StoreMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Optional;

/**
 * Service Implementation for managing {@link Store}.
 */
@Service
@Transactional
public class StoreServiceImpl implements StoreService {

    private final Logger log = LoggerFactory.getLogger(StoreServiceImpl.class);

    private final StoreRepository storeRepository;

    private final StoreMapper storeMapper;

    private final UserRepository userRepository;

    public StoreServiceImpl(StoreRepository storeRepository, StoreMapper storeMapper, UserRepository userRepository) {
        this.storeRepository = storeRepository;
        this.storeMapper = storeMapper;
        this.userRepository = userRepository;
    }

    /**
     * Save a store.
     *
     * @param storeDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public StoreDTO save(StoreDTO storeDTO) {
        log.debug("Request to save Store : {}", storeDTO);
        Store store = storeMapper.toEntity(storeDTO);
        long userId = storeDTO.getUserId();
        userRepository.findById(userId).ifPresent(store::user);
        store = storeRepository.save(store);
        return storeMapper.toDto(store);
    }

    /**
     * Get all the stores.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<StoreDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Stores");
        if (SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN)) {
            return storeRepository.findAll(pageable).map(storeMapper::toDto);

        } else if (SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.VIPO)) {
            return storeRepository.getAllStoresByVipo(pageable, SecurityUtils.getCurrentUserLogin().get())
                    .map(storeMapper::toDto);
        } else if (SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.USER)) {
            return storeRepository.getAllStoresByClient(pageable, SecurityUtils.getCurrentUserLogin().get())
                    .map(storeMapper::toDto);
        } else {
            return new PageImpl<StoreDTO>(new ArrayList<StoreDTO>(), pageable, 0);
        }

        // return storeRepository.findAll(pageable)
        // .map(storeMapper::toDto);
    }

    /**
     * Get one store by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<StoreDTO> findOne(Long id) {
        log.debug("Request to get Store : {}", id);
        return storeRepository.findById(id).map(storeMapper::toDto);
    }

    /**
     * Delete the store by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Store : {}", id);
        storeRepository.deleteById(id);
    }
}
