package com.memoire.kital.raph.service;

import com.memoire.kital.raph.service.dto.FactureDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.memoire.kital.raph.domain.Facture}.
 */
public interface FactureService {

    /**
     * Save a facture.
     *
     * @param factureDTO the entity to save.
     * @return the persisted entity.
     */
    FactureDTO save(FactureDTO factureDTO);

    /**
     * Get all the factures.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<FactureDTO> findAll(Pageable pageable);


    /**
     * Get the "id" facture.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<FactureDTO> findOne(String id);

    Optional<List<FactureDTO>> rechercheFactureByCode(String code);

    /**
     * Delete the "id" facture.
     *
     * @param id the id of the entity.
     */
    void delete(String id);
}
