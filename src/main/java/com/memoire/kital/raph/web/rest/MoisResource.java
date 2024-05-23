package com.memoire.kital.raph.web.rest;

import com.memoire.kital.raph.service.MoisService;
import com.memoire.kital.raph.web.rest.errors.BadRequestAlertException;
import com.memoire.kital.raph.service.dto.MoisDTO;

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
 * REST controller for managing {@link com.memoire.kital.raph.domain.Mois}.
 */
@RestController
@RequestMapping("/api")
public class MoisResource {

    private final Logger log = LoggerFactory.getLogger(MoisResource.class);

    private static final String ENTITY_NAME = "paiementdbMois";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MoisService moisService;

    public MoisResource(MoisService moisService) {
        this.moisService = moisService;
    }

    /**
     * {@code POST  /mois} : Create a new mois.
     *
     * @param moisDTO the moisDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new moisDTO, or with status {@code 400 (Bad Request)} if the mois has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/mois")
    public ResponseEntity<MoisDTO> createMois(@Valid @RequestBody MoisDTO moisDTO) throws URISyntaxException {
        log.debug("REST request to save Mois : {}", moisDTO);
        if (moisDTO.getId() != null) {
            throw new BadRequestAlertException("A new mois cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MoisDTO result = moisService.save(moisDTO);
        return ResponseEntity.created(new URI("/api/mois/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /mois} : Updates an existing mois.
     *
     * @param moisDTO the moisDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated moisDTO,
     * or with status {@code 400 (Bad Request)} if the moisDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the moisDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/mois")
    public ResponseEntity<MoisDTO> updateMois(@Valid @RequestBody MoisDTO moisDTO) throws URISyntaxException {
        log.debug("REST request to update Mois : {}", moisDTO);
        if (moisDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MoisDTO result = moisService.save(moisDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, moisDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /mois} : get all the mois.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of mois in body.
     */
    @GetMapping("/mois")
    public ResponseEntity<List<MoisDTO>> getAllMois(Pageable pageable) {
        log.debug("REST request to get a page of Mois");
        Page<MoisDTO> page = moisService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /mois/:id} : get the "id" mois.
     *
     * @param id the id of the moisDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the moisDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/mois/{id}")
    public ResponseEntity<MoisDTO> getMois(@PathVariable String id) {
        log.debug("REST request to get Mois : {}", id);
        Optional<MoisDTO> moisDTO = moisService.findOne(id);
        return ResponseUtil.wrapOrNotFound(moisDTO);
    }

    /**
     * {@code DELETE  /mois/:id} : delete the "id" mois.
     *
     * @param id the id of the moisDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/mois/{id}")
    public ResponseEntity<Void> deleteMois(@PathVariable String id) {
        log.debug("REST request to delete Mois : {}", id);
        moisService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
