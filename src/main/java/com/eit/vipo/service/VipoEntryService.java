package com.eit.vipo.service;

import com.eit.vipo.service.dto.VipoEntryDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.eit.vipo.domain.VipoEntry}.
 */
public interface VipoEntryService {

    /**
     * Save a vipoEntry.
     *
     * @param vipoEntryDTO the entity to save.
     * @return the persisted entity.
     */
    VipoEntryDTO save(VipoEntryDTO vipoEntryDTO);

    /**
     * Get all the vipoEntries.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<VipoEntryDTO> findAll(Pageable pageable);


    /**
     * Get the "id" vipoEntry.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<VipoEntryDTO> findOne(Long id);

    /**
     * Delete the "id" vipoEntry.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
