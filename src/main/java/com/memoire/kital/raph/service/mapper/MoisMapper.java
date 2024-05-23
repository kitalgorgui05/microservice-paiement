package com.memoire.kital.raph.service.mapper;


import com.memoire.kital.raph.domain.*;
import com.memoire.kital.raph.service.dto.MoisDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Mois} and its DTO {@link MoisDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface MoisMapper extends EntityMapper<MoisDTO, Mois> {


    @Mapping(target = "factures", ignore = true)
    //@Mapping(target = "removeFacture", ignore = true)
    Mois toEntity(MoisDTO moisDTO);

    default Mois fromId(String id) {
        if (id == null) {
            return null;
        }
        Mois mois = new Mois();
        mois.setId(id);
        return mois;
    }
}
