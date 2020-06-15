package com.eit.vipo.service;

import com.eit.vipo.service.dto.ImagePropertyDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.eit.vipo.domain.ImageProperty}.
 */
public interface ImagePropertyService {

    /**
     * Save a imageProperty.
     *
     * @param imagePropertyDTO the entity to save.
     * @return the persisted entity.
     */
    ImagePropertyDTO save(ImagePropertyDTO imagePropertyDTO);

    /**
     * Get all the imageProperties.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ImagePropertyDTO> findAll(Pageable pageable);


    /**
     * Get the "id" imageProperty.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ImagePropertyDTO> findOne(Long id);

    /**
     * Delete the "id" imageProperty.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
