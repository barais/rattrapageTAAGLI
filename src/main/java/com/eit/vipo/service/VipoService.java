package com.eit.vipo.service;

import com.eit.vipo.service.dto.VipoDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.eit.vipo.domain.Vipo}.
 */
public interface VipoService {

    /**
     * Save a vipo.
     *
     * @param vipoDTO the entity to save.
     * @return the persisted entity.
     */
    VipoDTO save(VipoDTO vipoDTO);

    /**
     * Get all the vipos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<VipoDTO> findAll(Pageable pageable);
    /**
     * Get all the VipoDTO where Store is {@code null}.
     *
     * @return the list of entities.
     */
    List<VipoDTO> findAllWhereStoreIsNull();


    /**
     * Get the "id" vipo.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<VipoDTO> findOne(Long id);

    /**
     * Delete the "id" vipo.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
