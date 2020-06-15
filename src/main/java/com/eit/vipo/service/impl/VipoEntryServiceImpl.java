package com.eit.vipo.service.impl;

import com.eit.vipo.service.VipoEntryService;
import com.eit.vipo.domain.Vipo;
import com.eit.vipo.domain.VipoEntry;
import com.eit.vipo.repository.VipoEntryRepository;
import com.eit.vipo.repository.VipoRepository;
import com.eit.vipo.security.AuthoritiesConstants;
import com.eit.vipo.security.SecurityUtils;
import com.eit.vipo.service.dto.VipoEntryDTO;
import com.eit.vipo.service.mapper.VipoEntryMapper;
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
 * Service Implementation for managing {@link VipoEntry}.
 */
@Service
@Transactional
public class VipoEntryServiceImpl implements VipoEntryService {

    private final Logger log = LoggerFactory.getLogger(VipoEntryServiceImpl.class);

    private final VipoEntryRepository vipoEntryRepository;

    private final VipoRepository vipoRepository;

    private final VipoEntryMapper vipoEntryMapper;

    public VipoEntryServiceImpl(VipoEntryRepository vipoEntryRepository, VipoRepository vipoRepository,
            VipoEntryMapper vipoEntryMapper) {
        this.vipoEntryRepository = vipoEntryRepository;
        this.vipoEntryMapper = vipoEntryMapper;
        this.vipoRepository = vipoRepository;
    }

    /**
     * Save a vipoEntry.
     *
     * @param vipoEntryDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public VipoEntryDTO save(VipoEntryDTO vipoEntryDTO) {
        log.debug("Request to save VipoEntry : {}", vipoEntryDTO);
        VipoEntry vipoEntry = vipoEntryMapper.toEntity(vipoEntryDTO);
        if (SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN)) {
            vipoEntry = vipoEntryRepository.save(vipoEntry);

        } else if (SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.VIPO)) {
            Vipo v = this.vipoRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin().get()).get();
            vipoEntry.setVipo(v);
            vipoEntry = vipoEntryRepository.save(vipoEntry);

        } else if (SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.USER)) {
            Vipo v = this.vipoRepository.findOneByLogin4Client(SecurityUtils.getCurrentUserLogin().get()).get();
            vipoEntry.setVipo(v);
            vipoEntry = vipoEntryRepository.save(vipoEntry);
        }

        return vipoEntryMapper.toDto(vipoEntry);
    }

    /**
     * Get all the vipoEntries.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<VipoEntryDTO> findAll(Pageable pageable) {
        log.debug("Request to get all VipoEntries");

        if (SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN)) {
            return vipoEntryRepository.findAll(pageable).map(vipoEntryMapper::toDto);

        } else if (SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.VIPO)) {
            return vipoEntryRepository.getAllVipoEntriesByVipo(pageable, SecurityUtils.getCurrentUserLogin().get())
                    .map(vipoEntryMapper::toDto);
        } else if (SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.USER)) {
            return vipoEntryRepository.getAllVipoEntriesByClient(pageable, SecurityUtils.getCurrentUserLogin().get())
                    .map(vipoEntryMapper::toDto);
        } else {
            return new PageImpl<VipoEntryDTO>(new ArrayList<VipoEntryDTO>(), pageable, 0);
        }

        // return vipoEntryRepository.findAll(pageable).map(vipoEntryMapper::toDto);
    }

    /**
     * Get one vipoEntry by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<VipoEntryDTO> findOne(Long id) {
        log.debug("Request to get VipoEntry : {}", id);
        return vipoEntryRepository.findById(id).map(vipoEntryMapper::toDto);
    }

    /**
     * Delete the vipoEntry by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete VipoEntry : {}", id);
        vipoEntryRepository.deleteById(id);
    }
}
