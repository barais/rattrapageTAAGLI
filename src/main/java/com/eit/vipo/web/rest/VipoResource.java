package com.eit.vipo.web.rest;

import com.eit.vipo.service.VipoService;
import com.eit.vipo.web.rest.errors.BadRequestAlertException;
import com.eit.vipo.service.dto.VipoDTO;

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
import java.util.Objects;
import java.util.Optional;
import java.util.stream.StreamSupport;

/**
 * REST controller for managing {@link com.eit.vipo.domain.Vipo}.
 */
@RestController
@RequestMapping("/api")
public class VipoResource {

    private final Logger log = LoggerFactory.getLogger(VipoResource.class);

    private static final String ENTITY_NAME = "vipo";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final VipoService vipoService;

    public VipoResource(VipoService vipoService) {
        this.vipoService = vipoService;
    }

    /**
     * {@code POST  /vipos} : Create a new vipo.
     *
     * @param vipoDTO the vipoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new vipoDTO, or with status {@code 400 (Bad Request)} if the vipo has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/vipos")
    public ResponseEntity<VipoDTO> createVipo(@Valid @RequestBody VipoDTO vipoDTO) throws URISyntaxException {
        log.debug("REST request to save Vipo : {}", vipoDTO);
        if (vipoDTO.getId() != null) {
            throw new BadRequestAlertException("A new vipo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        if (Objects.isNull(vipoDTO.getUserId())) {
            throw new BadRequestAlertException("Invalid association value provided", ENTITY_NAME, "null");
        }
        VipoDTO result = vipoService.save(vipoDTO);
        return ResponseEntity.created(new URI("/api/vipos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /vipos} : Updates an existing vipo.
     *
     * @param vipoDTO the vipoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated vipoDTO,
     * or with status {@code 400 (Bad Request)} if the vipoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the vipoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/vipos")
    public ResponseEntity<VipoDTO> updateVipo(@Valid @RequestBody VipoDTO vipoDTO) throws URISyntaxException {
        log.debug("REST request to update Vipo : {}", vipoDTO);
        if (vipoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        VipoDTO result = vipoService.save(vipoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, vipoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /vipos} : get all the vipos.
     *

     * @param pageable the pagination information.

     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of vipos in body.
     */
    @GetMapping("/vipos")
    public ResponseEntity<List<VipoDTO>> getAllVipos(Pageable pageable, @RequestParam(required = false) String filter) {
        if ("store-is-null".equals(filter)) {
            log.debug("REST request to get all Vipos where store is null");
            return new ResponseEntity<>(vipoService.findAllWhereStoreIsNull(),
                    HttpStatus.OK);
        }
        log.debug("REST request to get a page of Vipos");
        Page<VipoDTO> page = vipoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /vipos/:id} : get the "id" vipo.
     *
     * @param id the id of the vipoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the vipoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/vipos/{id}")
    public ResponseEntity<VipoDTO> getVipo(@PathVariable Long id) {
        log.debug("REST request to get Vipo : {}", id);
        Optional<VipoDTO> vipoDTO = vipoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(vipoDTO);
    }

    /**
     * {@code DELETE  /vipos/:id} : delete the "id" vipo.
     *
     * @param id the id of the vipoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/vipos/{id}")
    public ResponseEntity<Void> deleteVipo(@PathVariable Long id) {
        log.debug("REST request to delete Vipo : {}", id);
        vipoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
