package com.memoire.kital.raph.repository;

import com.memoire.kital.raph.domain.Reglement;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Reglement entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ReglementRepository extends JpaRepository<Reglement, String> {
}
