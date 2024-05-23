package com.memoire.kital.raph.service.dto;

import lombok.*;

import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link com.memoire.kital.raph.domain.Facture} entity.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class FactureDTO implements Serializable {
    private String id;
    private LocalDate date;
    private String code;
    private String paiee;
    private String idEleve;
    private String moisId;
    private String paiementId;
}
