package com.eit.vipo.service.impl;

import com.eit.vipo.service.ImagePropertyService;
import com.eit.vipo.domain.ImageProperty;
import com.eit.vipo.repository.ImagePropertyRepository;
import com.eit.vipo.security.AuthoritiesConstants;
import com.eit.vipo.security.SecurityUtils;
import com.eit.vipo.service.dto.ImagePropertyDTO;
import com.eit.vipo.service.mapper.ImagePropertyMapper;
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
 * Service Implementation for managing {@link ImageProperty}.
 */
@Service
@Transactional
public class ImagePropertyServiceImpl implements ImagePropertyService {

    private final Logger log = LoggerFactory.getLogger(ImagePropertyServiceImpl.class);

    private final ImagePropertyRepository imagePropertyRepository;

    private final ImagePropertyMapper imagePropertyMapper;

    public ImagePropertyServiceImpl(ImagePropertyRepository imagePropertyRepository,
            ImagePropertyMapper imagePropertyMapper) {
        this.imagePropertyRepository = imagePropertyRepository;
        this.imagePropertyMapper = imagePropertyMapper;
    }

    /**
     * Save a imageProperty.
     *
     * @param imagePropertyDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public ImagePropertyDTO save(ImagePropertyDTO imagePropertyDTO) {
        log.debug("Request to save ImageProperty : {}", imagePropertyDTO);
        ImageProperty imageProperty = imagePropertyMapper.toEntity(imagePropertyDTO);
        imageProperty = imagePropertyRepository.save(imageProperty);
        return imagePropertyMapper.toDto(imageProperty);
    }

    /**
     * Get all the imageProperties.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ImagePropertyDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ImageProperties");
        if (SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN)) {
            return imagePropertyRepository.findAll(pageable).map(imagePropertyMapper::toDto);
        } else if (SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.VIPO)) {
            return imagePropertyRepository
                    .getAllImagePropertiesByVipo(pageable, SecurityUtils.getCurrentUserLogin().get())
                    .map(imagePropertyMapper::toDto);
        } else if (SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.USER)) {
            return imagePropertyRepository
                    .getAllImagePropertiesByClient(pageable, SecurityUtils.getCurrentUserLogin().get())
                    .map(imagePropertyMapper::toDto);
        } else {
            return new PageImpl<ImagePropertyDTO>(new ArrayList<ImagePropertyDTO>(), pageable, 0);
        }

        // return imagePropertyRepository.findAll(pageable)
        // .map(imagePropertyMapper::toDto);
    }

    /**
     * Get one imageProperty by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ImagePropertyDTO> findOne(Long id) {
        log.debug("Request to get ImageProperty : {}", id);
        return imagePropertyRepository.findById(id).map(imagePropertyMapper::toDto);
    }

    /**
     * Delete the imageProperty by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ImageProperty : {}", id);
        imagePropertyRepository.deleteById(id);
    }
}
