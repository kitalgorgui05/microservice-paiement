package com.memoire.kital.raph.service.mapper;


import com.memoire.kital.raph.domain.*;
import com.memoire.kital.raph.service.dto.ReglementDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Reglement} and its DTO {@link ReglementDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ReglementMapper extends EntityMapper<ReglementDTO, Reglement> {


    @Mapping(target = "factures", ignore = true)
    //@Mapping(target = "removeFacture", ignore = true)
    Reglement toEntity(ReglementDTO reglementDTO);

    default Reglement fromId(String id) {
        if (id == null) {
            return null;
        }
        Reglement reglement = new Reglement();
        reglement.setId(id);
        return reglement;
    }
}
