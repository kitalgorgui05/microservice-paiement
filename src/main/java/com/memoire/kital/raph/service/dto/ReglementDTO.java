package com.memoire.kital.raph.service.dto;

import lombok.*;

import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link com.memoire.kital.raph.domain.Reglement} entity.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class ReglementDTO implements Serializable {
    private String id;
    private LocalDate date;
    private String montantInscription;
    private String mensualite;
    private String montantCantine;
    private String montantTransport;
    private String idNiveau;
    private String idAnnee;
}
