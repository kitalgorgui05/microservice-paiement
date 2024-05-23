package com.memoire.kital.raph.web.rest;

import com.memoire.kital.raph.service.FactureService;
import com.memoire.kital.raph.web.rest.errors.BadRequestAlertException;
import com.memoire.kital.raph.service.dto.FactureDTO;

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
 * REST controller for managing {@link com.memoire.kital.raph.domain.Facture}.
 */
@RestController
@RequestMapping("/api")
public class FactureResource {

    private final Logger log = LoggerFactory.getLogger(FactureResource.class);

    private static final String ENTITY_NAME = "paiementdbFacture";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FactureService factureService;

    public FactureResource(FactureService factureService) {
        this.factureService = factureService;
    }

    /**
     * {@code POST  /factures} : Create a new facture.
     *
     * @param factureDTO the factureDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new factureDTO, or with status {@code 400 (Bad Request)} if the facture has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/factures")
    public ResponseEntity<FactureDTO> createFacture(@Valid @RequestBody FactureDTO factureDTO) throws URISyntaxException {
        log.debug("REST request to save Facture : {}", factureDTO);
        if (factureDTO.getId() != null) {
            throw new BadRequestAlertException("A new facture cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FactureDTO result = factureService.save(factureDTO);
        return ResponseEntity.created(new URI("/api/factures/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /factures} : Updates an existing facture.
     *
     * @param factureDTO the factureDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated factureDTO,
     * or with status {@code 400 (Bad Request)} if the factureDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the factureDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/factures")
    public ResponseEntity<FactureDTO> updateFacture(@Valid @RequestBody FactureDTO factureDTO) throws URISyntaxException {
        log.debug("REST request to update Facture : {}", factureDTO);
        if (factureDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        FactureDTO result = factureService.save(factureDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, factureDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /factures} : get all the factures.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of factures in body.
     */
    @GetMapping("/factures")
    public ResponseEntity<List<FactureDTO>> getAllFactures(Pageable pageable) {
        log.debug("REST request to get a page of Factures");
        Page<FactureDTO> page = factureService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /factures/:id} : get the "id" facture.
     *
     * @param id the id of the factureDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the factureDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/factures/{id}")
    public ResponseEntity<FactureDTO> getFacture(@PathVariable String id) {
        log.debug("REST request to get Facture : {}", id);
        Optional<FactureDTO> factureDTO = factureService.findOne(id);
        return ResponseUtil.wrapOrNotFound(factureDTO);
    }

    @GetMapping("/factures/{code}")
    public ResponseEntity<List<FactureDTO>> rechercheFacture(@PathVariable String code) {
        log.debug("REST request to get Facture : {}", code);
        Optional<List<FactureDTO>> factureDTO = factureService.rechercheFactureByCode(code);
        return ResponseUtil.wrapOrNotFound(factureDTO);
    }
    @DeleteMapping("/factures/{id}")
    public ResponseEntity<Void> deleteFacture(@PathVariable String id) {
        log.debug("REST request to delete Facture : {}", id);
        factureService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
