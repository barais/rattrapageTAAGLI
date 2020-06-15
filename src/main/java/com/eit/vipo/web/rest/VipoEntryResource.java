package com.eit.vipo.web.rest;

import com.eit.vipo.service.VipoEntryService;
import com.eit.vipo.web.rest.errors.BadRequestAlertException;
import com.eit.vipo.service.dto.VipoEntryDTO;

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
 * REST controller for managing {@link com.eit.vipo.domain.VipoEntry}.
 */
@RestController
@RequestMapping("/api")
public class VipoEntryResource {

    private final Logger log = LoggerFactory.getLogger(VipoEntryResource.class);

    private static final String ENTITY_NAME = "vipoEntry";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final VipoEntryService vipoEntryService;

    public VipoEntryResource(VipoEntryService vipoEntryService) {
        this.vipoEntryService = vipoEntryService;
    }

    /**
     * {@code POST  /vipo-entries} : Create a new vipoEntry.
     *
     * @param vipoEntryDTO the vipoEntryDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new vipoEntryDTO, or with status {@code 400 (Bad Request)} if the vipoEntry has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/vipo-entries")
    public ResponseEntity<VipoEntryDTO> createVipoEntry(@Valid @RequestBody VipoEntryDTO vipoEntryDTO) throws URISyntaxException {
        log.debug("REST request to save VipoEntry : {}", vipoEntryDTO);
        if (vipoEntryDTO.getId() != null) {
            throw new BadRequestAlertException("A new vipoEntry cannot already have an ID", ENTITY_NAME, "idexists");
        }
        VipoEntryDTO result = vipoEntryService.save(vipoEntryDTO);
        return ResponseEntity.created(new URI("/api/vipo-entries/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /vipo-entries} : Updates an existing vipoEntry.
     *
     * @param vipoEntryDTO the vipoEntryDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated vipoEntryDTO,
     * or with status {@code 400 (Bad Request)} if the vipoEntryDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the vipoEntryDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/vipo-entries")
    public ResponseEntity<VipoEntryDTO> updateVipoEntry(@Valid @RequestBody VipoEntryDTO vipoEntryDTO) throws URISyntaxException {
        log.debug("REST request to update VipoEntry : {}", vipoEntryDTO);
        if (vipoEntryDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        VipoEntryDTO result = vipoEntryService.save(vipoEntryDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, vipoEntryDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /vipo-entries} : get all the vipoEntries.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of vipoEntries in body.
     */
    @GetMapping("/vipo-entries")
    public ResponseEntity<List<VipoEntryDTO>> getAllVipoEntries(Pageable pageable) {
        log.debug("REST request to get a page of VipoEntries");
        Page<VipoEntryDTO> page = vipoEntryService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /vipo-entries/:id} : get the "id" vipoEntry.
     *
     * @param id the id of the vipoEntryDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the vipoEntryDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/vipo-entries/{id}")
    public ResponseEntity<VipoEntryDTO> getVipoEntry(@PathVariable Long id) {
        log.debug("REST request to get VipoEntry : {}", id);
        Optional<VipoEntryDTO> vipoEntryDTO = vipoEntryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(vipoEntryDTO);
    }

    /**
     * {@code DELETE  /vipo-entries/:id} : delete the "id" vipoEntry.
     *
     * @param id the id of the vipoEntryDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/vipo-entries/{id}")
    public ResponseEntity<Void> deleteVipoEntry(@PathVariable Long id) {
        log.debug("REST request to delete VipoEntry : {}", id);
        vipoEntryService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
