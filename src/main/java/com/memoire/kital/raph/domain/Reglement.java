package com.memoire.kital.raph.domain;

import lombok.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * A Reglement.
 */
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Table(name = "reglement")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Reglement implements Serializable {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String id;

    @NotNull
    @Column(name = "date", nullable = false)
    private LocalDate date;

    @NotNull
    @Column(name = "montant_inscription", nullable = false)
    private String montantInscription;

    @NotNull
    @Column(name = "mensualite", nullable = false)
    private String mensualite;

    @NotNull
    @Column(name = "montant_cantine", nullable = false)
    private Long montantCantine;

    @NotNull
    @Column(name = "montant_transport", nullable = false)
    private Long montantTransport;

    @NotNull
    @Column(name = "id_niveau", nullable = false)
    private String idNiveau;

    @NotNull
    @Column(name = "id_annee", nullable = false)
    private String idAnnee;

    @OneToMany(mappedBy = "paiement")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Facture> factures = new HashSet<>();
}
