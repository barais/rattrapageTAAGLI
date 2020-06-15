package com.eit.vipo.web.rest;

import com.eit.vipo.VipoApp;
import com.eit.vipo.domain.VipoEntry;
import com.eit.vipo.repository.VipoEntryRepository;
import com.eit.vipo.service.VipoEntryService;
import com.eit.vipo.service.dto.VipoEntryDTO;
import com.eit.vipo.service.mapper.VipoEntryMapper;
import com.eit.vipo.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static com.eit.vipo.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link VipoEntryResource} REST controller.
 */
@SpringBootTest(classes = VipoApp.class)
public class VipoEntryResourceIT {

    private static final LocalDate DEFAULT_REGISTER_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_REGISTER_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_IMAGE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_IMAGE_NAME = "BBBBBBBBBB";

    @Autowired
    private VipoEntryRepository vipoEntryRepository;

    @Autowired
    private VipoEntryMapper vipoEntryMapper;

    @Autowired
    private VipoEntryService vipoEntryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restVipoEntryMockMvc;

    private VipoEntry vipoEntry;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final VipoEntryResource vipoEntryResource = new VipoEntryResource(vipoEntryService);
        this.restVipoEntryMockMvc = MockMvcBuilders.standaloneSetup(vipoEntryResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static VipoEntry createEntity(EntityManager em) {
        VipoEntry vipoEntry = new VipoEntry()
            .registerDate(DEFAULT_REGISTER_DATE)
            .imageName(DEFAULT_IMAGE_NAME);
        return vipoEntry;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static VipoEntry createUpdatedEntity(EntityManager em) {
        VipoEntry vipoEntry = new VipoEntry()
            .registerDate(UPDATED_REGISTER_DATE)
            .imageName(UPDATED_IMAGE_NAME);
        return vipoEntry;
    }

    @BeforeEach
    public void initTest() {
        vipoEntry = createEntity(em);
    }

    @Test
    @Transactional
    public void createVipoEntry() throws Exception {
        int databaseSizeBeforeCreate = vipoEntryRepository.findAll().size();

        // Create the VipoEntry
        VipoEntryDTO vipoEntryDTO = vipoEntryMapper.toDto(vipoEntry);
        restVipoEntryMockMvc.perform(post("/api/vipo-entries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vipoEntryDTO)))
            .andExpect(status().isCreated());

        // Validate the VipoEntry in the database
        List<VipoEntry> vipoEntryList = vipoEntryRepository.findAll();
        assertThat(vipoEntryList).hasSize(databaseSizeBeforeCreate + 1);
        VipoEntry testVipoEntry = vipoEntryList.get(vipoEntryList.size() - 1);
        assertThat(testVipoEntry.getRegisterDate()).isEqualTo(DEFAULT_REGISTER_DATE);
        assertThat(testVipoEntry.getImageName()).isEqualTo(DEFAULT_IMAGE_NAME);
    }

    @Test
    @Transactional
    public void createVipoEntryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = vipoEntryRepository.findAll().size();

        // Create the VipoEntry with an existing ID
        vipoEntry.setId(1L);
        VipoEntryDTO vipoEntryDTO = vipoEntryMapper.toDto(vipoEntry);

        // An entity with an existing ID cannot be created, so this API call must fail
        restVipoEntryMockMvc.perform(post("/api/vipo-entries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vipoEntryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the VipoEntry in the database
        List<VipoEntry> vipoEntryList = vipoEntryRepository.findAll();
        assertThat(vipoEntryList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkRegisterDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = vipoEntryRepository.findAll().size();
        // set the field null
        vipoEntry.setRegisterDate(null);

        // Create the VipoEntry, which fails.
        VipoEntryDTO vipoEntryDTO = vipoEntryMapper.toDto(vipoEntry);

        restVipoEntryMockMvc.perform(post("/api/vipo-entries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vipoEntryDTO)))
            .andExpect(status().isBadRequest());

        List<VipoEntry> vipoEntryList = vipoEntryRepository.findAll();
        assertThat(vipoEntryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkImageNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = vipoEntryRepository.findAll().size();
        // set the field null
        vipoEntry.setImageName(null);

        // Create the VipoEntry, which fails.
        VipoEntryDTO vipoEntryDTO = vipoEntryMapper.toDto(vipoEntry);

        restVipoEntryMockMvc.perform(post("/api/vipo-entries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vipoEntryDTO)))
            .andExpect(status().isBadRequest());

        List<VipoEntry> vipoEntryList = vipoEntryRepository.findAll();
        assertThat(vipoEntryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllVipoEntries() throws Exception {
        // Initialize the database
        vipoEntryRepository.saveAndFlush(vipoEntry);

        // Get all the vipoEntryList
        restVipoEntryMockMvc.perform(get("/api/vipo-entries?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(vipoEntry.getId().intValue())))
            .andExpect(jsonPath("$.[*].registerDate").value(hasItem(DEFAULT_REGISTER_DATE.toString())))
            .andExpect(jsonPath("$.[*].imageName").value(hasItem(DEFAULT_IMAGE_NAME)));
    }
    
    @Test
    @Transactional
    public void getVipoEntry() throws Exception {
        // Initialize the database
        vipoEntryRepository.saveAndFlush(vipoEntry);

        // Get the vipoEntry
        restVipoEntryMockMvc.perform(get("/api/vipo-entries/{id}", vipoEntry.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(vipoEntry.getId().intValue()))
            .andExpect(jsonPath("$.registerDate").value(DEFAULT_REGISTER_DATE.toString()))
            .andExpect(jsonPath("$.imageName").value(DEFAULT_IMAGE_NAME));
    }

    @Test
    @Transactional
    public void getNonExistingVipoEntry() throws Exception {
        // Get the vipoEntry
        restVipoEntryMockMvc.perform(get("/api/vipo-entries/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateVipoEntry() throws Exception {
        // Initialize the database
        vipoEntryRepository.saveAndFlush(vipoEntry);

        int databaseSizeBeforeUpdate = vipoEntryRepository.findAll().size();

        // Update the vipoEntry
        VipoEntry updatedVipoEntry = vipoEntryRepository.findById(vipoEntry.getId()).get();
        // Disconnect from session so that the updates on updatedVipoEntry are not directly saved in db
        em.detach(updatedVipoEntry);
        updatedVipoEntry
            .registerDate(UPDATED_REGISTER_DATE)
            .imageName(UPDATED_IMAGE_NAME);
        VipoEntryDTO vipoEntryDTO = vipoEntryMapper.toDto(updatedVipoEntry);

        restVipoEntryMockMvc.perform(put("/api/vipo-entries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vipoEntryDTO)))
            .andExpect(status().isOk());

        // Validate the VipoEntry in the database
        List<VipoEntry> vipoEntryList = vipoEntryRepository.findAll();
        assertThat(vipoEntryList).hasSize(databaseSizeBeforeUpdate);
        VipoEntry testVipoEntry = vipoEntryList.get(vipoEntryList.size() - 1);
        assertThat(testVipoEntry.getRegisterDate()).isEqualTo(UPDATED_REGISTER_DATE);
        assertThat(testVipoEntry.getImageName()).isEqualTo(UPDATED_IMAGE_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingVipoEntry() throws Exception {
        int databaseSizeBeforeUpdate = vipoEntryRepository.findAll().size();

        // Create the VipoEntry
        VipoEntryDTO vipoEntryDTO = vipoEntryMapper.toDto(vipoEntry);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVipoEntryMockMvc.perform(put("/api/vipo-entries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vipoEntryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the VipoEntry in the database
        List<VipoEntry> vipoEntryList = vipoEntryRepository.findAll();
        assertThat(vipoEntryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteVipoEntry() throws Exception {
        // Initialize the database
        vipoEntryRepository.saveAndFlush(vipoEntry);

        int databaseSizeBeforeDelete = vipoEntryRepository.findAll().size();

        // Delete the vipoEntry
        restVipoEntryMockMvc.perform(delete("/api/vipo-entries/{id}", vipoEntry.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<VipoEntry> vipoEntryList = vipoEntryRepository.findAll();
        assertThat(vipoEntryList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
