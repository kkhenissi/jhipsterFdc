package com.kkhenissi.fdc.web.rest;

import com.kkhenissi.fdc.JhipsterFdcApp;
import com.kkhenissi.fdc.domain.Brand;
import com.kkhenissi.fdc.repository.BrandRepository;
import com.kkhenissi.fdc.service.BrandService;

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
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link BrandResource} REST controller.
 */
@SpringBootTest(classes = JhipsterFdcApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class BrandResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Instant DEFAULT_START_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_START_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private BrandRepository brandRepository;

    @Autowired
    private BrandService brandService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBrandMockMvc;

    private Brand brand;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Brand createEntity(EntityManager em) {
        Brand brand = new Brand()
            .name(DEFAULT_NAME)
            .startDate(DEFAULT_START_DATE);
        return brand;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Brand createUpdatedEntity(EntityManager em) {
        Brand brand = new Brand()
            .name(UPDATED_NAME)
            .startDate(UPDATED_START_DATE);
        return brand;
    }

    @BeforeEach
    public void initTest() {
        brand = createEntity(em);
    }

    @Test
    @Transactional
    public void createBrand() throws Exception {
        int databaseSizeBeforeCreate = brandRepository.findAll().size();
        // Create the Brand
        restBrandMockMvc.perform(post("/api/brands")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(brand)))
            .andExpect(status().isCreated());

        // Validate the Brand in the database
        List<Brand> brandList = brandRepository.findAll();
        assertThat(brandList).hasSize(databaseSizeBeforeCreate + 1);
        Brand testBrand = brandList.get(brandList.size() - 1);
        assertThat(testBrand.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testBrand.getStartDate()).isEqualTo(DEFAULT_START_DATE);
    }

    @Test
    @Transactional
    public void createBrandWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = brandRepository.findAll().size();

        // Create the Brand with an existing ID
        brand.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBrandMockMvc.perform(post("/api/brands")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(brand)))
            .andExpect(status().isBadRequest());

        // Validate the Brand in the database
        List<Brand> brandList = brandRepository.findAll();
        assertThat(brandList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = brandRepository.findAll().size();
        // set the field null
        brand.setName(null);

        // Create the Brand, which fails.


        restBrandMockMvc.perform(post("/api/brands")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(brand)))
            .andExpect(status().isBadRequest());

        List<Brand> brandList = brandRepository.findAll();
        assertThat(brandList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllBrands() throws Exception {
        // Initialize the database
        brandRepository.saveAndFlush(brand);

        // Get all the brandList
        restBrandMockMvc.perform(get("/api/brands?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(brand.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())));
    }
    
    @Test
    @Transactional
    public void getBrand() throws Exception {
        // Initialize the database
        brandRepository.saveAndFlush(brand);

        // Get the brand
        restBrandMockMvc.perform(get("/api/brands/{id}", brand.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(brand.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.startDate").value(DEFAULT_START_DATE.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingBrand() throws Exception {
        // Get the brand
        restBrandMockMvc.perform(get("/api/brands/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBrand() throws Exception {
        // Initialize the database
        brandService.save(brand);

        int databaseSizeBeforeUpdate = brandRepository.findAll().size();

        // Update the brand
        Brand updatedBrand = brandRepository.findById(brand.getId()).get();
        // Disconnect from session so that the updates on updatedBrand are not directly saved in db
        em.detach(updatedBrand);
        updatedBrand
            .name(UPDATED_NAME)
            .startDate(UPDATED_START_DATE);

        restBrandMockMvc.perform(put("/api/brands")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedBrand)))
            .andExpect(status().isOk());

        // Validate the Brand in the database
        List<Brand> brandList = brandRepository.findAll();
        assertThat(brandList).hasSize(databaseSizeBeforeUpdate);
        Brand testBrand = brandList.get(brandList.size() - 1);
        assertThat(testBrand.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testBrand.getStartDate()).isEqualTo(UPDATED_START_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingBrand() throws Exception {
        int databaseSizeBeforeUpdate = brandRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBrandMockMvc.perform(put("/api/brands")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(brand)))
            .andExpect(status().isBadRequest());

        // Validate the Brand in the database
        List<Brand> brandList = brandRepository.findAll();
        assertThat(brandList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteBrand() throws Exception {
        // Initialize the database
        brandService.save(brand);

        int databaseSizeBeforeDelete = brandRepository.findAll().size();

        // Delete the brand
        restBrandMockMvc.perform(delete("/api/brands/{id}", brand.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Brand> brandList = brandRepository.findAll();
        assertThat(brandList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
