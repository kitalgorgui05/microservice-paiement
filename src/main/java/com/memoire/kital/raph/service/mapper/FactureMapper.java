package com.memoire.kital.raph.service.mapper;


import com.memoire.kital.raph.domain.*;
import com.memoire.kital.raph.service.dto.FactureDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Facture} and its DTO {@link FactureDTO}.
 */
@Mapper(componentModel = "spring", uses = {MoisMapper.class, ReglementMapper.class})
public interface FactureMapper extends EntityMapper<FactureDTO, Facture> {

    @Mapping(source = "mois.id", target = "moisId")
    @Mapping(source = "paiement.id", target = "paiementId")
    FactureDTO toDto(Facture facture);

    @Mapping(source = "moisId", target = "mois")
    @Mapping(source = "paiementId", target = "paiement")
    Facture toEntity(FactureDTO factureDTO);

    default Facture fromId(String id) {
        if (id == null) {
            return null;
        }
        Facture facture = new Facture();
        facture.setId(id);
        return facture;
    }
}
