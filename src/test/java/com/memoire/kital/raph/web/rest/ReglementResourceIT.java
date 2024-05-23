package com.memoire.kital.raph.web.rest;

import com.memoire.kital.raph.PaiementdbApp;
import com.memoire.kital.raph.config.TestSecurityConfiguration;
import com.memoire.kital.raph.domain.Reglement;
import com.memoire.kital.raph.repository.ReglementRepository;
import com.memoire.kital.raph.service.ReglementService;
import com.memoire.kital.raph.service.dto.ReglementDTO;
import com.memoire.kital.raph.service.mapper.ReglementMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link ReglementResource} REST controller.
 */
@SpringBootTest(classes = { PaiementdbApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class ReglementResourceIT {

    private static final LocalDate DEFAULT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_MONTANT_INSCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_MONTANT_INSCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_MENSUALITE = "AAAAAAAAAA";
    private static final String UPDATED_MENSUALITE = "BBBBBBBBBB";

    private static final Long DEFAULT_MONTANT_CANTINE = 1L;
    private static final Long UPDATED_MONTANT_CANTINE = 2L;

    private static final Long DEFAULT_MONTANT_TRANSPORT = 1L;
    private static final Long UPDATED_MONTANT_TRANSPORT = 2L;

    private static final String DEFAULT_ID_NIVEAU = "AAAAAAAAAA";
    private static final String UPDATED_ID_NIVEAU = "BBBBBBBBBB";

    private static final String DEFAULT_ID_ANNEE = "AAAAAAAAAA";
    private static final String UPDATED_ID_ANNEE = "BBBBBBBBBB";

    @Autowired
    private ReglementRepository reglementRepository;

    @Autowired
    private ReglementMapper reglementMapper;

    @Autowired
    private ReglementService reglementService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restReglementMockMvc;

    private Reglement reglement;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Reglement createEntity(EntityManager em) {
        Reglement reglement = new Reglement()
            .date(DEFAULT_DATE)
            .montantInscription(DEFAULT_MONTANT_INSCRIPTION)
            .mensualite(DEFAULT_MENSUALITE)
            .montantCantine(DEFAULT_MONTANT_CANTINE)
            .montantTransport(DEFAULT_MONTANT_TRANSPORT)
            .idNiveau(DEFAULT_ID_NIVEAU)
            .idAnnee(DEFAULT_ID_ANNEE);
        return reglement;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Reglement createUpdatedEntity(EntityManager em) {
        Reglement reglement = new Reglement()
            .date(UPDATED_DATE)
            .montantInscription(UPDATED_MONTANT_INSCRIPTION)
            .mensualite(UPDATED_MENSUALITE)
            .montantCantine(UPDATED_MONTANT_CANTINE)
            .montantTransport(UPDATED_MONTANT_TRANSPORT)
            .idNiveau(UPDATED_ID_NIVEAU)
            .idAnnee(UPDATED_ID_ANNEE);
        return reglement;
    }

    @BeforeEach
    public void initTest() {
        reglement = createEntity(em);
    }

    @Test
    @Transactional
    public void createReglement() throws Exception {
        int databaseSizeBeforeCreate = reglementRepository.findAll().size();
        // Create the Reglement
        ReglementDTO reglementDTO = reglementMapper.toDto(reglement);
        restReglementMockMvc.perform(post("/api/reglements").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(reglementDTO)))
            .andExpect(status().isCreated());

        // Validate the Reglement in the database
        List<Reglement> reglementList = reglementRepository.findAll();
        assertThat(reglementList).hasSize(databaseSizeBeforeCreate + 1);
        Reglement testReglement = reglementList.get(reglementList.size() - 1);
        assertThat(testReglement.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testReglement.getMontantInscription()).isEqualTo(DEFAULT_MONTANT_INSCRIPTION);
        assertThat(testReglement.getMensualite()).isEqualTo(DEFAULT_MENSUALITE);
        assertThat(testReglement.getMontantCantine()).isEqualTo(DEFAULT_MONTANT_CANTINE);
        assertThat(testReglement.getMontantTransport()).isEqualTo(DEFAULT_MONTANT_TRANSPORT);
        assertThat(testReglement.getIdNiveau()).isEqualTo(DEFAULT_ID_NIVEAU);
        assertThat(testReglement.getIdAnnee()).isEqualTo(DEFAULT_ID_ANNEE);
    }

    @Test
    @Transactional
    public void createReglementWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = reglementRepository.findAll().size();

        // Create the Reglement with an existing ID
        reglement.setId(1L);
        ReglementDTO reglementDTO = reglementMapper.toDto(reglement);

        // An entity with an existing ID cannot be created, so this API call must fail
        restReglementMockMvc.perform(post("/api/reglements").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(reglementDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Reglement in the database
        List<Reglement> reglementList = reglementRepository.findAll();
        assertThat(reglementList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = reglementRepository.findAll().size();
        // set the field null
        reglement.setDate(null);

        // Create the Reglement, which fails.
        ReglementDTO reglementDTO = reglementMapper.toDto(reglement);


        restReglementMockMvc.perform(post("/api/reglements").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(reglementDTO)))
            .andExpect(status().isBadRequest());

        List<Reglement> reglementList = reglementRepository.findAll();
        assertThat(reglementList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMontantInscriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = reglementRepository.findAll().size();
        // set the field null
        reglement.setMontantInscription(null);

        // Create the Reglement, which fails.
        ReglementDTO reglementDTO = reglementMapper.toDto(reglement);


        restReglementMockMvc.perform(post("/api/reglements").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(reglementDTO)))
            .andExpect(status().isBadRequest());

        List<Reglement> reglementList = reglementRepository.findAll();
        assertThat(reglementList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMensualiteIsRequired() throws Exception {
        int databaseSizeBeforeTest = reglementRepository.findAll().size();
        // set the field null
        reglement.setMensualite(null);

        // Create the Reglement, which fails.
        ReglementDTO reglementDTO = reglementMapper.toDto(reglement);


        restReglementMockMvc.perform(post("/api/reglements").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(reglementDTO)))
            .andExpect(status().isBadRequest());

        List<Reglement> reglementList = reglementRepository.findAll();
        assertThat(reglementList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMontantCantineIsRequired() throws Exception {
        int databaseSizeBeforeTest = reglementRepository.findAll().size();
        // set the field null
        reglement.setMontantCantine(null);

        // Create the Reglement, which fails.
        ReglementDTO reglementDTO = reglementMapper.toDto(reglement);


        restReglementMockMvc.perform(post("/api/reglements").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(reglementDTO)))
            .andExpect(status().isBadRequest());

        List<Reglement> reglementList = reglementRepository.findAll();
        assertThat(reglementList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMontantTransportIsRequired() throws Exception {
        int databaseSizeBeforeTest = reglementRepository.findAll().size();
        // set the field null
        reglement.setMontantTransport(null);

        // Create the Reglement, which fails.
        ReglementDTO reglementDTO = reglementMapper.toDto(reglement);


        restReglementMockMvc.perform(post("/api/reglements").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(reglementDTO)))
            .andExpect(status().isBadRequest());

        List<Reglement> reglementList = reglementRepository.findAll();
        assertThat(reglementList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIdNiveauIsRequired() throws Exception {
        int databaseSizeBeforeTest = reglementRepository.findAll().size();
        // set the field null
        reglement.setIdNiveau(null);

        // Create the Reglement, which fails.
        ReglementDTO reglementDTO = reglementMapper.toDto(reglement);


        restReglementMockMvc.perform(post("/api/reglements").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(reglementDTO)))
            .andExpect(status().isBadRequest());

        List<Reglement> reglementList = reglementRepository.findAll();
        assertThat(reglementList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIdAnneeIsRequired() throws Exception {
        int databaseSizeBeforeTest = reglementRepository.findAll().size();
        // set the field null
        reglement.setIdAnnee(null);

        // Create the Reglement, which fails.
        ReglementDTO reglementDTO = reglementMapper.toDto(reglement);


        restReglementMockMvc.perform(post("/api/reglements").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(reglementDTO)))
            .andExpect(status().isBadRequest());

        List<Reglement> reglementList = reglementRepository.findAll();
        assertThat(reglementList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllReglements() throws Exception {
        // Initialize the database
        reglementRepository.saveAndFlush(reglement);

        // Get all the reglementList
        restReglementMockMvc.perform(get("/api/reglements?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(reglement.getId().intValue())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].montantInscription").value(hasItem(DEFAULT_MONTANT_INSCRIPTION)))
            .andExpect(jsonPath("$.[*].mensualite").value(hasItem(DEFAULT_MENSUALITE)))
            .andExpect(jsonPath("$.[*].montantCantine").value(hasItem(DEFAULT_MONTANT_CANTINE.intValue())))
            .andExpect(jsonPath("$.[*].montantTransport").value(hasItem(DEFAULT_MONTANT_TRANSPORT.intValue())))
            .andExpect(jsonPath("$.[*].idNiveau").value(hasItem(DEFAULT_ID_NIVEAU)))
            .andExpect(jsonPath("$.[*].idAnnee").value(hasItem(DEFAULT_ID_ANNEE)));
    }
    
    @Test
    @Transactional
    public void getReglement() throws Exception {
        // Initialize the database
        reglementRepository.saveAndFlush(reglement);

        // Get the reglement
        restReglementMockMvc.perform(get("/api/reglements/{id}", reglement.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(reglement.getId().intValue()))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()))
            .andExpect(jsonPath("$.montantInscription").value(DEFAULT_MONTANT_INSCRIPTION))
            .andExpect(jsonPath("$.mensualite").value(DEFAULT_MENSUALITE))
            .andExpect(jsonPath("$.montantCantine").value(DEFAULT_MONTANT_CANTINE.intValue()))
            .andExpect(jsonPath("$.montantTransport").value(DEFAULT_MONTANT_TRANSPORT.intValue()))
            .andExpect(jsonPath("$.idNiveau").value(DEFAULT_ID_NIVEAU))
            .andExpect(jsonPath("$.idAnnee").value(DEFAULT_ID_ANNEE));
    }
    @Test
    @Transactional
    public void getNonExistingReglement() throws Exception {
        // Get the reglement
        restReglementMockMvc.perform(get("/api/reglements/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateReglement() throws Exception {
        // Initialize the database
        reglementRepository.saveAndFlush(reglement);

        int databaseSizeBeforeUpdate = reglementRepository.findAll().size();

        // Update the reglement
        Reglement updatedReglement = reglementRepository.findById(reglement.getId()).get();
        // Disconnect from session so that the updates on updatedReglement are not directly saved in db
        em.detach(updatedReglement);
        updatedReglement
            .date(UPDATED_DATE)
            .montantInscription(UPDATED_MONTANT_INSCRIPTION)
            .mensualite(UPDATED_MENSUALITE)
            .montantCantine(UPDATED_MONTANT_CANTINE)
            .montantTransport(UPDATED_MONTANT_TRANSPORT)
            .idNiveau(UPDATED_ID_NIVEAU)
            .idAnnee(UPDATED_ID_ANNEE);
        ReglementDTO reglementDTO = reglementMapper.toDto(updatedReglement);

        restReglementMockMvc.perform(put("/api/reglements").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(reglementDTO)))
            .andExpect(status().isOk());

        // Validate the Reglement in the database
        List<Reglement> reglementList = reglementRepository.findAll();
        assertThat(reglementList).hasSize(databaseSizeBeforeUpdate);
        Reglement testReglement = reglementList.get(reglementList.size() - 1);
        assertThat(testReglement.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testReglement.getMontantInscription()).isEqualTo(UPDATED_MONTANT_INSCRIPTION);
        assertThat(testReglement.getMensualite()).isEqualTo(UPDATED_MENSUALITE);
        assertThat(testReglement.getMontantCantine()).isEqualTo(UPDATED_MONTANT_CANTINE);
        assertThat(testReglement.getMontantTransport()).isEqualTo(UPDATED_MONTANT_TRANSPORT);
        assertThat(testReglement.getIdNiveau()).isEqualTo(UPDATED_ID_NIVEAU);
        assertThat(testReglement.getIdAnnee()).isEqualTo(UPDATED_ID_ANNEE);
    }

    @Test
    @Transactional
    public void updateNonExistingReglement() throws Exception {
        int databaseSizeBeforeUpdate = reglementRepository.findAll().size();

        // Create the Reglement
        ReglementDTO reglementDTO = reglementMapper.toDto(reglement);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restReglementMockMvc.perform(put("/api/reglements").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(reglementDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Reglement in the database
        List<Reglement> reglementList = reglementRepository.findAll();
        assertThat(reglementList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteReglement() throws Exception {
        // Initialize the database
        reglementRepository.saveAndFlush(reglement);

        int databaseSizeBeforeDelete = reglementRepository.findAll().size();

        // Delete the reglement
        restReglementMockMvc.perform(delete("/api/reglements/{id}", reglement.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Reglement> reglementList = reglementRepository.findAll();
        assertThat(reglementList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
