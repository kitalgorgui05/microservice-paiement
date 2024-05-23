package com.memoire.kital.raph.service.dto;

import lombok.*;

import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link com.memoire.kital.raph.domain.Mois} entity.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class MoisDTO implements Serializable {

    private String id;
    private String nom;
}
