package com.memoire.kital.raph.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.boot.actuate.trace.http.Include;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * A Facture.
 */
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Table(name = "facture")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Facture implements Serializable {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(name = "id", unique = true,updatable = false,nullable = false)
    private String id;

    @NotNull
    @Column(name = "date", nullable = false)
    private LocalDate date;

    @Column(name = "code")
    private String code;

    @NotNull
    @Column(name = "paiee", nullable = false)
    private String paiee;

    @NotNull
    @Column(name = "id_eleve", nullable = false,length = 32)
    private String idEleve;

    @ManyToOne
    @JsonIgnoreProperties(value = "factures", allowSetters = true)
    private Mois mois;

    @ManyToOne
    @JsonIgnoreProperties(value = "factures", allowSetters = true)
    private Reglement paiement;
}
