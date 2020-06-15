package com.eit.vipo.web.rest;

import com.eit.vipo.VipoApp;
import com.eit.vipo.domain.ImageProperty;
import com.eit.vipo.repository.ImagePropertyRepository;
import com.eit.vipo.service.ImagePropertyService;
import com.eit.vipo.service.dto.ImagePropertyDTO;
import com.eit.vipo.service.mapper.ImagePropertyMapper;
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
import java.util.List;

import static com.eit.vipo.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link ImagePropertyResource} REST controller.
 */
@SpringBootTest(classes = VipoApp.class)
public class ImagePropertyResourceIT {

    private static final String DEFAULT_LABEL = "AAAAAAAAAA";
    private static final String UPDATED_LABEL = "BBBBBBBBBB";

    private static final Integer DEFAULT_HEIGHT = 1;
    private static final Integer UPDATED_HEIGHT = 2;

    private static final Integer DEFAULT_WIDTH = 1;
    private static final Integer UPDATED_WIDTH = 2;

    private static final Integer DEFAULT_X = 1;
    private static final Integer UPDATED_X = 2;

    private static final Integer DEFAULT_Y = 1;
    private static final Integer UPDATED_Y = 2;

    private static final String DEFAULT_H_VG_COLOR = "AAAAAAAAAA";
    private static final String UPDATED_H_VG_COLOR = "BBBBBBBBBB";

    @Autowired
    private ImagePropertyRepository imagePropertyRepository;

    @Autowired
    private ImagePropertyMapper imagePropertyMapper;

    @Autowired
    private ImagePropertyService imagePropertyService;

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

    private MockMvc restImagePropertyMockMvc;

    private ImageProperty imageProperty;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ImagePropertyResource imagePropertyResource = new ImagePropertyResource(imagePropertyService);
        this.restImagePropertyMockMvc = MockMvcBuilders.standaloneSetup(imagePropertyResource)
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
    public static ImageProperty createEntity(EntityManager em) {
        ImageProperty imageProperty = new ImageProperty()
            .label(DEFAULT_LABEL)
            .height(DEFAULT_HEIGHT)
            .width(DEFAULT_WIDTH)
            .x(DEFAULT_X)
            .y(DEFAULT_Y)
            .hVGColor(DEFAULT_H_VG_COLOR);
        return imageProperty;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ImageProperty createUpdatedEntity(EntityManager em) {
        ImageProperty imageProperty = new ImageProperty()
            .label(UPDATED_LABEL)
            .height(UPDATED_HEIGHT)
            .width(UPDATED_WIDTH)
            .x(UPDATED_X)
            .y(UPDATED_Y)
            .hVGColor(UPDATED_H_VG_COLOR);
        return imageProperty;
    }

    @BeforeEach
    public void initTest() {
        imageProperty = createEntity(em);
    }

    @Test
    @Transactional
    public void createImageProperty() throws Exception {
        int databaseSizeBeforeCreate = imagePropertyRepository.findAll().size();

        // Create the ImageProperty
        ImagePropertyDTO imagePropertyDTO = imagePropertyMapper.toDto(imageProperty);
        restImagePropertyMockMvc.perform(post("/api/image-properties")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(imagePropertyDTO)))
            .andExpect(status().isCreated());

        // Validate the ImageProperty in the database
        List<ImageProperty> imagePropertyList = imagePropertyRepository.findAll();
        assertThat(imagePropertyList).hasSize(databaseSizeBeforeCreate + 1);
        ImageProperty testImageProperty = imagePropertyList.get(imagePropertyList.size() - 1);
        assertThat(testImageProperty.getLabel()).isEqualTo(DEFAULT_LABEL);
        assertThat(testImageProperty.getHeight()).isEqualTo(DEFAULT_HEIGHT);
        assertThat(testImageProperty.getWidth()).isEqualTo(DEFAULT_WIDTH);
        assertThat(testImageProperty.getX()).isEqualTo(DEFAULT_X);
        assertThat(testImageProperty.getY()).isEqualTo(DEFAULT_Y);
        assertThat(testImageProperty.gethVGColor()).isEqualTo(DEFAULT_H_VG_COLOR);
    }

    @Test
    @Transactional
    public void createImagePropertyWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = imagePropertyRepository.findAll().size();

        // Create the ImageProperty with an existing ID
        imageProperty.setId(1L);
        ImagePropertyDTO imagePropertyDTO = imagePropertyMapper.toDto(imageProperty);

        // An entity with an existing ID cannot be created, so this API call must fail
        restImagePropertyMockMvc.perform(post("/api/image-properties")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(imagePropertyDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ImageProperty in the database
        List<ImageProperty> imagePropertyList = imagePropertyRepository.findAll();
        assertThat(imagePropertyList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkLabelIsRequired() throws Exception {
        int databaseSizeBeforeTest = imagePropertyRepository.findAll().size();
        // set the field null
        imageProperty.setLabel(null);

        // Create the ImageProperty, which fails.
        ImagePropertyDTO imagePropertyDTO = imagePropertyMapper.toDto(imageProperty);

        restImagePropertyMockMvc.perform(post("/api/image-properties")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(imagePropertyDTO)))
            .andExpect(status().isBadRequest());

        List<ImageProperty> imagePropertyList = imagePropertyRepository.findAll();
        assertThat(imagePropertyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkHeightIsRequired() throws Exception {
        int databaseSizeBeforeTest = imagePropertyRepository.findAll().size();
        // set the field null
        imageProperty.setHeight(null);

        // Create the ImageProperty, which fails.
        ImagePropertyDTO imagePropertyDTO = imagePropertyMapper.toDto(imageProperty);

        restImagePropertyMockMvc.perform(post("/api/image-properties")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(imagePropertyDTO)))
            .andExpect(status().isBadRequest());

        List<ImageProperty> imagePropertyList = imagePropertyRepository.findAll();
        assertThat(imagePropertyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkWidthIsRequired() throws Exception {
        int databaseSizeBeforeTest = imagePropertyRepository.findAll().size();
        // set the field null
        imageProperty.setWidth(null);

        // Create the ImageProperty, which fails.
        ImagePropertyDTO imagePropertyDTO = imagePropertyMapper.toDto(imageProperty);

        restImagePropertyMockMvc.perform(post("/api/image-properties")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(imagePropertyDTO)))
            .andExpect(status().isBadRequest());

        List<ImageProperty> imagePropertyList = imagePropertyRepository.findAll();
        assertThat(imagePropertyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkXIsRequired() throws Exception {
        int databaseSizeBeforeTest = imagePropertyRepository.findAll().size();
        // set the field null
        imageProperty.setX(null);

        // Create the ImageProperty, which fails.
        ImagePropertyDTO imagePropertyDTO = imagePropertyMapper.toDto(imageProperty);

        restImagePropertyMockMvc.perform(post("/api/image-properties")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(imagePropertyDTO)))
            .andExpect(status().isBadRequest());

        List<ImageProperty> imagePropertyList = imagePropertyRepository.findAll();
        assertThat(imagePropertyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkYIsRequired() throws Exception {
        int databaseSizeBeforeTest = imagePropertyRepository.findAll().size();
        // set the field null
        imageProperty.setY(null);

        // Create the ImageProperty, which fails.
        ImagePropertyDTO imagePropertyDTO = imagePropertyMapper.toDto(imageProperty);

        restImagePropertyMockMvc.perform(post("/api/image-properties")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(imagePropertyDTO)))
            .andExpect(status().isBadRequest());

        List<ImageProperty> imagePropertyList = imagePropertyRepository.findAll();
        assertThat(imagePropertyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllImageProperties() throws Exception {
        // Initialize the database
        imagePropertyRepository.saveAndFlush(imageProperty);

        // Get all the imagePropertyList
        restImagePropertyMockMvc.perform(get("/api/image-properties?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(imageProperty.getId().intValue())))
            .andExpect(jsonPath("$.[*].label").value(hasItem(DEFAULT_LABEL)))
            .andExpect(jsonPath("$.[*].height").value(hasItem(DEFAULT_HEIGHT)))
            .andExpect(jsonPath("$.[*].width").value(hasItem(DEFAULT_WIDTH)))
            .andExpect(jsonPath("$.[*].x").value(hasItem(DEFAULT_X)))
            .andExpect(jsonPath("$.[*].y").value(hasItem(DEFAULT_Y)))
            .andExpect(jsonPath("$.[*].hVGColor").value(hasItem(DEFAULT_H_VG_COLOR)));
    }
    
    @Test
    @Transactional
    public void getImageProperty() throws Exception {
        // Initialize the database
        imagePropertyRepository.saveAndFlush(imageProperty);

        // Get the imageProperty
        restImagePropertyMockMvc.perform(get("/api/image-properties/{id}", imageProperty.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(imageProperty.getId().intValue()))
            .andExpect(jsonPath("$.label").value(DEFAULT_LABEL))
            .andExpect(jsonPath("$.height").value(DEFAULT_HEIGHT))
            .andExpect(jsonPath("$.width").value(DEFAULT_WIDTH))
            .andExpect(jsonPath("$.x").value(DEFAULT_X))
            .andExpect(jsonPath("$.y").value(DEFAULT_Y))
            .andExpect(jsonPath("$.hVGColor").value(DEFAULT_H_VG_COLOR));
    }

    @Test
    @Transactional
    public void getNonExistingImageProperty() throws Exception {
        // Get the imageProperty
        restImagePropertyMockMvc.perform(get("/api/image-properties/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateImageProperty() throws Exception {
        // Initialize the database
        imagePropertyRepository.saveAndFlush(imageProperty);

        int databaseSizeBeforeUpdate = imagePropertyRepository.findAll().size();

        // Update the imageProperty
        ImageProperty updatedImageProperty = imagePropertyRepository.findById(imageProperty.getId()).get();
        // Disconnect from session so that the updates on updatedImageProperty are not directly saved in db
        em.detach(updatedImageProperty);
        updatedImageProperty
            .label(UPDATED_LABEL)
            .height(UPDATED_HEIGHT)
            .width(UPDATED_WIDTH)
            .x(UPDATED_X)
            .y(UPDATED_Y)
            .hVGColor(UPDATED_H_VG_COLOR);
        ImagePropertyDTO imagePropertyDTO = imagePropertyMapper.toDto(updatedImageProperty);

        restImagePropertyMockMvc.perform(put("/api/image-properties")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(imagePropertyDTO)))
            .andExpect(status().isOk());

        // Validate the ImageProperty in the database
        List<ImageProperty> imagePropertyList = imagePropertyRepository.findAll();
        assertThat(imagePropertyList).hasSize(databaseSizeBeforeUpdate);
        ImageProperty testImageProperty = imagePropertyList.get(imagePropertyList.size() - 1);
        assertThat(testImageProperty.getLabel()).isEqualTo(UPDATED_LABEL);
        assertThat(testImageProperty.getHeight()).isEqualTo(UPDATED_HEIGHT);
        assertThat(testImageProperty.getWidth()).isEqualTo(UPDATED_WIDTH);
        assertThat(testImageProperty.getX()).isEqualTo(UPDATED_X);
        assertThat(testImageProperty.getY()).isEqualTo(UPDATED_Y);
        assertThat(testImageProperty.gethVGColor()).isEqualTo(UPDATED_H_VG_COLOR);
    }

    @Test
    @Transactional
    public void updateNonExistingImageProperty() throws Exception {
        int databaseSizeBeforeUpdate = imagePropertyRepository.findAll().size();

        // Create the ImageProperty
        ImagePropertyDTO imagePropertyDTO = imagePropertyMapper.toDto(imageProperty);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restImagePropertyMockMvc.perform(put("/api/image-properties")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(imagePropertyDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ImageProperty in the database
        List<ImageProperty> imagePropertyList = imagePropertyRepository.findAll();
        assertThat(imagePropertyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteImageProperty() throws Exception {
        // Initialize the database
        imagePropertyRepository.saveAndFlush(imageProperty);

        int databaseSizeBeforeDelete = imagePropertyRepository.findAll().size();

        // Delete the imageProperty
        restImagePropertyMockMvc.perform(delete("/api/image-properties/{id}", imageProperty.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ImageProperty> imagePropertyList = imagePropertyRepository.findAll();
        assertThat(imagePropertyList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
