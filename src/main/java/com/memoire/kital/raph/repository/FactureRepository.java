package com.memoire.kital.raph.repository;

import com.memoire.kital.raph.domain.Facture;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the Facture entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FactureRepository extends JpaRepository<Facture, String> {
    List<Facture> getFactureByCodeContains(String code);
}
