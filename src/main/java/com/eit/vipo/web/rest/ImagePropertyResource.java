package com.eit.vipo.web.rest;

import com.eit.vipo.service.ImagePropertyService;
import com.eit.vipo.web.rest.errors.BadRequestAlertException;
import com.eit.vipo.service.dto.ImagePropertyDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.eit.vipo.domain.ImageProperty}.
 */
@RestController
@RequestMapping("/api")
public class ImagePropertyResource {

    private final Logger log = LoggerFactory.getLogger(ImagePropertyResource.class);

    private static final String ENTITY_NAME = "imageProperty";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ImagePropertyService imagePropertyService;

    public ImagePropertyResource(ImagePropertyService imagePropertyService) {
        this.imagePropertyService = imagePropertyService;
    }

    /**
     * {@code POST  /image-properties} : Create a new imageProperty.
     *
     * @param imagePropertyDTO the imagePropertyDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new imagePropertyDTO, or with status {@code 400 (Bad Request)} if the imageProperty has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/image-properties")
    public ResponseEntity<ImagePropertyDTO> createImageProperty(@Valid @RequestBody ImagePropertyDTO imagePropertyDTO) throws URISyntaxException {
        log.debug("REST request to save ImageProperty : {}", imagePropertyDTO);
        if (imagePropertyDTO.getId() != null) {
            throw new BadRequestAlertException("A new imageProperty cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ImagePropertyDTO result = imagePropertyService.save(imagePropertyDTO);
        return ResponseEntity.created(new URI("/api/image-properties/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /image-properties} : Updates an existing imageProperty.
     *
     * @param imagePropertyDTO the imagePropertyDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated imagePropertyDTO,
     * or with status {@code 400 (Bad Request)} if the imagePropertyDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the imagePropertyDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/image-properties")
    public ResponseEntity<ImagePropertyDTO> updateImageProperty(@Valid @RequestBody ImagePropertyDTO imagePropertyDTO) throws URISyntaxException {
        log.debug("REST request to update ImageProperty : {}", imagePropertyDTO);
        if (imagePropertyDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ImagePropertyDTO result = imagePropertyService.save(imagePropertyDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, imagePropertyDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /image-properties} : get all the imageProperties.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of imageProperties in body.
     */
    @GetMapping("/image-properties")
    public ResponseEntity<List<ImagePropertyDTO>> getAllImageProperties(Pageable pageable) {
        log.debug("REST request to get a page of ImageProperties");
        Page<ImagePropertyDTO> page = imagePropertyService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /image-properties/:id} : get the "id" imageProperty.
     *
     * @param id the id of the imagePropertyDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the imagePropertyDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/image-properties/{id}")
    public ResponseEntity<ImagePropertyDTO> getImageProperty(@PathVariable Long id) {
        log.debug("REST request to get ImageProperty : {}", id);
        Optional<ImagePropertyDTO> imagePropertyDTO = imagePropertyService.findOne(id);
        return ResponseUtil.wrapOrNotFound(imagePropertyDTO);
    }

    /**
     * {@code DELETE  /image-properties/:id} : delete the "id" imageProperty.
     *
     * @param id the id of the imagePropertyDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/image-properties/{id}")
    public ResponseEntity<Void> deleteImageProperty(@PathVariable Long id) {
        log.debug("REST request to delete ImageProperty : {}", id);
        imagePropertyService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
