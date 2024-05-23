package com.memoire.kital.raph.web.rest;

import com.memoire.kital.raph.service.ReglementService;
import com.memoire.kital.raph.web.rest.errors.BadRequestAlertException;
import com.memoire.kital.raph.service.dto.ReglementDTO;

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
 * REST controller for managing {@link com.memoire.kital.raph.domain.Reglement}.
 */
@RestController
@RequestMapping("/api")
public class ReglementResource {

    private final Logger log = LoggerFactory.getLogger(ReglementResource.class);

    private static final String ENTITY_NAME = "paiementdbReglement";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ReglementService reglementService;

    public ReglementResource(ReglementService reglementService) {
        this.reglementService = reglementService;
    }

    /**
     * {@code POST  /reglements} : Create a new reglement.
     *
     * @param reglementDTO the reglementDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new reglementDTO, or with status {@code 400 (Bad Request)} if the reglement has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/reglements")
    public ResponseEntity<ReglementDTO> createReglement(@Valid @RequestBody ReglementDTO reglementDTO) throws URISyntaxException {
        log.debug("REST request to save Reglement : {}", reglementDTO);
        if (reglementDTO.getId() != null) {
            throw new BadRequestAlertException("A new reglement cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ReglementDTO result = reglementService.save(reglementDTO);
        return ResponseEntity.created(new URI("/api/reglements/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /reglements} : Updates an existing reglement.
     *
     * @param reglementDTO the reglementDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated reglementDTO,
     * or with status {@code 400 (Bad Request)} if the reglementDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the reglementDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/reglements")
    public ResponseEntity<ReglementDTO> updateReglement(@Valid @RequestBody ReglementDTO reglementDTO) throws URISyntaxException {
        log.debug("REST request to update Reglement : {}", reglementDTO);
        if (reglementDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ReglementDTO result = reglementService.save(reglementDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, reglementDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /reglements} : get all the reglements.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of reglements in body.
     */
    @GetMapping("/reglements")
    public ResponseEntity<List<ReglementDTO>> getAllReglements(Pageable pageable) {
        log.debug("REST request to get a page of Reglements");
        Page<ReglementDTO> page = reglementService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /reglements/:id} : get the "id" reglement.
     *
     * @param id the id of the reglementDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the reglementDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/reglements/{id}")
    public ResponseEntity<ReglementDTO> getReglement(@PathVariable String id) {
        log.debug("REST request to get Reglement : {}", id);
        Optional<ReglementDTO> reglementDTO = reglementService.findOne(id);
        return ResponseUtil.wrapOrNotFound(reglementDTO);
    }

    /**
     * {@code DELETE  /reglements/:id} : delete the "id" reglement.
     *
     * @param id the id of the reglementDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/reglements/{id}")
    public ResponseEntity<Void> deleteReglement(@PathVariable String id) {
        log.debug("REST request to delete Reglement : {}", id);
        reglementService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
