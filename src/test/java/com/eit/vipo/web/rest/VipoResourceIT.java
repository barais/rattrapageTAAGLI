package com.eit.vipo.web.rest;

import com.eit.vipo.VipoApp;
import com.eit.vipo.domain.Vipo;
import com.eit.vipo.domain.User;
import com.eit.vipo.repository.VipoRepository;
import com.eit.vipo.service.VipoService;
import com.eit.vipo.service.dto.VipoDTO;
import com.eit.vipo.service.mapper.VipoMapper;
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
 * Integration tests for the {@link VipoResource} REST controller.
 */
@SpringBootTest(classes = VipoApp.class)
public class VipoResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LONGITUDE = "AAAAAAAAAA";
    private static final String UPDATED_LONGITUDE = "BBBBBBBBBB";

    private static final String DEFAULT_LATTITUDE = "AAAAAAAAAA";
    private static final String UPDATED_LATTITUDE = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_CREATED_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATED_DATE = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private VipoRepository vipoRepository;

    @Autowired
    private VipoMapper vipoMapper;

    @Autowired
    private VipoService vipoService;

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

    private MockMvc restVipoMockMvc;

    private Vipo vipo;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final VipoResource vipoResource = new VipoResource(vipoService);
        this.restVipoMockMvc = MockMvcBuilders.standaloneSetup(vipoResource)
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
    public static Vipo createEntity(EntityManager em) {
        Vipo vipo = new Vipo()
            .name(DEFAULT_NAME)
            .longitude(DEFAULT_LONGITUDE)
            .lattitude(DEFAULT_LATTITUDE)
            .createdDate(DEFAULT_CREATED_DATE);
        // Add required entity
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        vipo.setUser(user);
        return vipo;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Vipo createUpdatedEntity(EntityManager em) {
        Vipo vipo = new Vipo()
            .name(UPDATED_NAME)
            .longitude(UPDATED_LONGITUDE)
            .lattitude(UPDATED_LATTITUDE)
            .createdDate(UPDATED_CREATED_DATE);
        // Add required entity
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        vipo.setUser(user);
        return vipo;
    }

    @BeforeEach
    public void initTest() {
        vipo = createEntity(em);
    }

    @Test
    @Transactional
    public void createVipo() throws Exception {
        int databaseSizeBeforeCreate = vipoRepository.findAll().size();

        // Create the Vipo
        VipoDTO vipoDTO = vipoMapper.toDto(vipo);
        restVipoMockMvc.perform(post("/api/vipos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vipoDTO)))
            .andExpect(status().isCreated());

        // Validate the Vipo in the database
        List<Vipo> vipoList = vipoRepository.findAll();
        assertThat(vipoList).hasSize(databaseSizeBeforeCreate + 1);
        Vipo testVipo = vipoList.get(vipoList.size() - 1);
        assertThat(testVipo.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testVipo.getLongitude()).isEqualTo(DEFAULT_LONGITUDE);
        assertThat(testVipo.getLattitude()).isEqualTo(DEFAULT_LATTITUDE);
        assertThat(testVipo.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);

        // Validate the id for MapsId, the ids must be same
        assertThat(testVipo.getId()).isEqualTo(testVipo.getUser().getId());
    }

    @Test
    @Transactional
    public void createVipoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = vipoRepository.findAll().size();

        // Create the Vipo with an existing ID
        vipo.setId(1L);
        VipoDTO vipoDTO = vipoMapper.toDto(vipo);

        // An entity with an existing ID cannot be created, so this API call must fail
        restVipoMockMvc.perform(post("/api/vipos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vipoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Vipo in the database
        List<Vipo> vipoList = vipoRepository.findAll();
        assertThat(vipoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void updateVipoMapsIdAssociationWithNewId() throws Exception {
        // Initialize the database
        vipoRepository.saveAndFlush(vipo);
        int databaseSizeBeforeCreate = vipoRepository.findAll().size();

        // Add a new parent entity
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();

        // Load the vipo
        Vipo updatedVipo = vipoRepository.findById(vipo.getId()).get();
        // Disconnect from session so that the updates on updatedVipo are not directly saved in db
        em.detach(updatedVipo);

        // Update the User with new association value
        updatedVipo.setUser(user);
        VipoDTO updatedVipoDTO = vipoMapper.toDto(updatedVipo);

        // Update the entity
        restVipoMockMvc.perform(put("/api/vipos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedVipoDTO)))
            .andExpect(status().isOk());

        // Validate the Vipo in the database
        List<Vipo> vipoList = vipoRepository.findAll();
        assertThat(vipoList).hasSize(databaseSizeBeforeCreate);
        Vipo testVipo = vipoList.get(vipoList.size() - 1);

        // Validate the id for MapsId, the ids must be same
        // Uncomment the following line for assertion. However, please note that there is a known issue and uncommenting will fail the test.
        // Please look at https://github.com/jhipster/generator-jhipster/issues/9100. You can modify this test as necessary.
        // assertThat(testVipo.getId()).isEqualTo(testVipo.getUser().getId());
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = vipoRepository.findAll().size();
        // set the field null
        vipo.setName(null);

        // Create the Vipo, which fails.
        VipoDTO vipoDTO = vipoMapper.toDto(vipo);

        restVipoMockMvc.perform(post("/api/vipos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vipoDTO)))
            .andExpect(status().isBadRequest());

        List<Vipo> vipoList = vipoRepository.findAll();
        assertThat(vipoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreatedDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = vipoRepository.findAll().size();
        // set the field null
        vipo.setCreatedDate(null);

        // Create the Vipo, which fails.
        VipoDTO vipoDTO = vipoMapper.toDto(vipo);

        restVipoMockMvc.perform(post("/api/vipos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vipoDTO)))
            .andExpect(status().isBadRequest());

        List<Vipo> vipoList = vipoRepository.findAll();
        assertThat(vipoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllVipos() throws Exception {
        // Initialize the database
        vipoRepository.saveAndFlush(vipo);

        // Get all the vipoList
        restVipoMockMvc.perform(get("/api/vipos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(vipo.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].longitude").value(hasItem(DEFAULT_LONGITUDE)))
            .andExpect(jsonPath("$.[*].lattitude").value(hasItem(DEFAULT_LATTITUDE)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())));
    }
    
    @Test
    @Transactional
    public void getVipo() throws Exception {
        // Initialize the database
        vipoRepository.saveAndFlush(vipo);

        // Get the vipo
        restVipoMockMvc.perform(get("/api/vipos/{id}", vipo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(vipo.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.longitude").value(DEFAULT_LONGITUDE))
            .andExpect(jsonPath("$.lattitude").value(DEFAULT_LATTITUDE))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingVipo() throws Exception {
        // Get the vipo
        restVipoMockMvc.perform(get("/api/vipos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateVipo() throws Exception {
        // Initialize the database
        vipoRepository.saveAndFlush(vipo);

        int databaseSizeBeforeUpdate = vipoRepository.findAll().size();

        // Update the vipo
        Vipo updatedVipo = vipoRepository.findById(vipo.getId()).get();
        // Disconnect from session so that the updates on updatedVipo are not directly saved in db
        em.detach(updatedVipo);
        updatedVipo
            .name(UPDATED_NAME)
            .longitude(UPDATED_LONGITUDE)
            .lattitude(UPDATED_LATTITUDE)
            .createdDate(UPDATED_CREATED_DATE);
        VipoDTO vipoDTO = vipoMapper.toDto(updatedVipo);

        restVipoMockMvc.perform(put("/api/vipos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vipoDTO)))
            .andExpect(status().isOk());

        // Validate the Vipo in the database
        List<Vipo> vipoList = vipoRepository.findAll();
        assertThat(vipoList).hasSize(databaseSizeBeforeUpdate);
        Vipo testVipo = vipoList.get(vipoList.size() - 1);
        assertThat(testVipo.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testVipo.getLongitude()).isEqualTo(UPDATED_LONGITUDE);
        assertThat(testVipo.getLattitude()).isEqualTo(UPDATED_LATTITUDE);
        assertThat(testVipo.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingVipo() throws Exception {
        int databaseSizeBeforeUpdate = vipoRepository.findAll().size();

        // Create the Vipo
        VipoDTO vipoDTO = vipoMapper.toDto(vipo);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVipoMockMvc.perform(put("/api/vipos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vipoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Vipo in the database
        List<Vipo> vipoList = vipoRepository.findAll();
        assertThat(vipoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteVipo() throws Exception {
        // Initialize the database
        vipoRepository.saveAndFlush(vipo);

        int databaseSizeBeforeDelete = vipoRepository.findAll().size();

        // Delete the vipo
        restVipoMockMvc.perform(delete("/api/vipos/{id}", vipo.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Vipo> vipoList = vipoRepository.findAll();
        assertThat(vipoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
