package com.memoire.kital.raph.service;

import com.memoire.kital.raph.service.dto.ReglementDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.memoire.kital.raph.domain.Reglement}.
 */
public interface ReglementService {

    /**
     * Save a reglement.
     *
     * @param reglementDTO the entity to save.
     * @return the persisted entity.
     */
    ReglementDTO save(ReglementDTO reglementDTO);

    /**
     * Get all the reglements.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ReglementDTO> findAll(Pageable pageable);


    /**
     * Get the "id" reglement.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ReglementDTO> findOne(String id);

    /**
     * Delete the "id" reglement.
     *
     * @param id the id of the entity.
     */
    void delete(String id);
}
