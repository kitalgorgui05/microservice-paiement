package com.memoire.kital.raph.web.rest;

import com.memoire.kital.raph.PaiementdbApp;
import com.memoire.kital.raph.config.TestSecurityConfiguration;
import com.memoire.kital.raph.domain.Mois;
import com.memoire.kital.raph.repository.MoisRepository;
import com.memoire.kital.raph.service.MoisService;
import com.memoire.kital.raph.service.dto.MoisDTO;
import com.memoire.kital.raph.service.mapper.MoisMapper;

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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link MoisResource} REST controller.
 */
@SpringBootTest(classes = { PaiementdbApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class MoisResourceIT {

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    @Autowired
    private MoisRepository moisRepository;

    @Autowired
    private MoisMapper moisMapper;

    @Autowired
    private MoisService moisService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMoisMockMvc;

    private Mois mois;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Mois createEntity(EntityManager em) {
        Mois mois = new Mois()
            .nom(DEFAULT_NOM);
        return mois;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Mois createUpdatedEntity(EntityManager em) {
        Mois mois = new Mois()
            .nom(UPDATED_NOM);
        return mois;
    }

    @BeforeEach
    public void initTest() {
        mois = createEntity(em);
    }

    @Test
    @Transactional
    public void createMois() throws Exception {
        int databaseSizeBeforeCreate = moisRepository.findAll().size();
        // Create the Mois
        MoisDTO moisDTO = moisMapper.toDto(mois);
        restMoisMockMvc.perform(post("/api/mois").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(moisDTO)))
            .andExpect(status().isCreated());

        // Validate the Mois in the database
        List<Mois> moisList = moisRepository.findAll();
        assertThat(moisList).hasSize(databaseSizeBeforeCreate + 1);
        Mois testMois = moisList.get(moisList.size() - 1);
        assertThat(testMois.getNom()).isEqualTo(DEFAULT_NOM);
    }

    @Test
    @Transactional
    public void createMoisWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = moisRepository.findAll().size();

        // Create the Mois with an existing ID
        mois.setId(1L);
        MoisDTO moisDTO = moisMapper.toDto(mois);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMoisMockMvc.perform(post("/api/mois").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(moisDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Mois in the database
        List<Mois> moisList = moisRepository.findAll();
        assertThat(moisList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNomIsRequired() throws Exception {
        int databaseSizeBeforeTest = moisRepository.findAll().size();
        // set the field null
        mois.setNom(null);

        // Create the Mois, which fails.
        MoisDTO moisDTO = moisMapper.toDto(mois);


        restMoisMockMvc.perform(post("/api/mois").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(moisDTO)))
            .andExpect(status().isBadRequest());

        List<Mois> moisList = moisRepository.findAll();
        assertThat(moisList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMois() throws Exception {
        // Initialize the database
        moisRepository.saveAndFlush(mois);

        // Get all the moisList
        restMoisMockMvc.perform(get("/api/mois?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mois.getId().intValue())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM)));
    }
    
    @Test
    @Transactional
    public void getMois() throws Exception {
        // Initialize the database
        moisRepository.saveAndFlush(mois);

        // Get the mois
        restMoisMockMvc.perform(get("/api/mois/{id}", mois.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(mois.getId().intValue()))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM));
    }
    @Test
    @Transactional
    public void getNonExistingMois() throws Exception {
        // Get the mois
        restMoisMockMvc.perform(get("/api/mois/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMois() throws Exception {
        // Initialize the database
        moisRepository.saveAndFlush(mois);

        int databaseSizeBeforeUpdate = moisRepository.findAll().size();

        // Update the mois
        Mois updatedMois = moisRepository.findById(mois.getId()).get();
        // Disconnect from session so that the updates on updatedMois are not directly saved in db
        em.detach(updatedMois);
        updatedMois
            .nom(UPDATED_NOM);
        MoisDTO moisDTO = moisMapper.toDto(updatedMois);

        restMoisMockMvc.perform(put("/api/mois").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(moisDTO)))
            .andExpect(status().isOk());

        // Validate the Mois in the database
        List<Mois> moisList = moisRepository.findAll();
        assertThat(moisList).hasSize(databaseSizeBeforeUpdate);
        Mois testMois = moisList.get(moisList.size() - 1);
        assertThat(testMois.getNom()).isEqualTo(UPDATED_NOM);
    }

    @Test
    @Transactional
    public void updateNonExistingMois() throws Exception {
        int databaseSizeBeforeUpdate = moisRepository.findAll().size();

        // Create the Mois
        MoisDTO moisDTO = moisMapper.toDto(mois);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMoisMockMvc.perform(put("/api/mois").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(moisDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Mois in the database
        List<Mois> moisList = moisRepository.findAll();
        assertThat(moisList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMois() throws Exception {
        // Initialize the database
        moisRepository.saveAndFlush(mois);

        int databaseSizeBeforeDelete = moisRepository.findAll().size();

        // Delete the mois
        restMoisMockMvc.perform(delete("/api/mois/{id}", mois.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Mois> moisList = moisRepository.findAll();
        assertThat(moisList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
