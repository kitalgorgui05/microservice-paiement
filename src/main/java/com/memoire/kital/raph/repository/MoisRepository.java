package com.memoire.kital.raph.repository;

import com.memoire.kital.raph.domain.Mois;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Mois entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MoisRepository extends JpaRepository<Mois, String> {
}
