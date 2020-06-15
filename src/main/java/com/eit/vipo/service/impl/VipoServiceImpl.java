package com.eit.vipo.service.impl;

import com.eit.vipo.service.VipoService;
import com.eit.vipo.domain.Vipo;
import com.eit.vipo.repository.VipoRepository;
import com.eit.vipo.security.AuthoritiesConstants;
import com.eit.vipo.security.SecurityUtils;
import com.eit.vipo.repository.UserRepository;
import com.eit.vipo.service.dto.VipoDTO;
import com.eit.vipo.service.mapper.VipoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Service Implementation for managing {@link Vipo}.
 */
@Service
@Transactional
public class VipoServiceImpl implements VipoService {

    private final Logger log = LoggerFactory.getLogger(VipoServiceImpl.class);

    private final VipoRepository vipoRepository;

    private final VipoMapper vipoMapper;

    private final UserRepository userRepository;

    public VipoServiceImpl(VipoRepository vipoRepository, VipoMapper vipoMapper, UserRepository userRepository) {
        this.vipoRepository = vipoRepository;
        this.vipoMapper = vipoMapper;
        this.userRepository = userRepository;
    }

    /**
     * Save a vipo.
     *
     * @param vipoDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public VipoDTO save(VipoDTO vipoDTO) {
        log.debug("Request to save Vipo : {}", vipoDTO);
        Vipo vipo = vipoMapper.toEntity(vipoDTO);
        long userId = vipoDTO.getUserId();
        userRepository.findById(userId).ifPresent(vipo::user);
        vipo = vipoRepository.save(vipo);
        return vipoMapper.toDto(vipo);
    }

    /**
     * Get all the vipos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<VipoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Vipos");

        if (SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN)) {
            return vipoRepository.findAll(pageable).map(vipoMapper::toDto);

        } else if (SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.VIPO)) {
            return vipoRepository.getAllViposByVipo(pageable, SecurityUtils.getCurrentUserLogin().get())
                    .map(vipoMapper::toDto);
        } else if (SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.USER)) {
            return vipoRepository.getAllViposByClient(pageable, SecurityUtils.getCurrentUserLogin().get())
                    .map(vipoMapper::toDto);
        } else {
            return new PageImpl<VipoDTO>(new ArrayList<VipoDTO>(), pageable, 0);
        }

    }

    /**
     * Get all the vipos where Store is {@code null}.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<VipoDTO> findAllWhereStoreIsNull() {
        log.debug("Request to get all vipos where Store is null");
        return StreamSupport.stream(vipoRepository.findAll().spliterator(), false)
                .filter(vipo -> vipo.getStore() == null).map(vipoMapper::toDto)
                .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one vipo by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<VipoDTO> findOne(Long id) {
        log.debug("Request to get Vipo : {}", id);
        return vipoRepository.findById(id).map(vipoMapper::toDto);
    }

    /**
     * Delete the vipo by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Vipo : {}", id);
        vipoRepository.deleteById(id);
    }
}
