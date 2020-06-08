package com.kkhenissi.fdc.web.rest;

import com.kkhenissi.fdc.JhipsterFdcApp;
import com.kkhenissi.fdc.domain.FdcUser;
import com.kkhenissi.fdc.repository.FdcUserRepository;

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
 * Integration tests for the {@link FdcUserResource} REST controller.
 */
@SpringBootTest(classes = JhipsterFdcApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class FdcUserResourceIT {

    private static final String DEFAULT_FIRST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FIRST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LAST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_PHONE_NUMBER = "BBBBBBBBBB";

    private static final Instant DEFAULT_HIRE_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_HIRE_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private FdcUserRepository fdcUserRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFdcUserMockMvc;

    private FdcUser fdcUser;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FdcUser createEntity(EntityManager em) {
        FdcUser fdcUser = new FdcUser()
            .firstName(DEFAULT_FIRST_NAME)
            .lastName(DEFAULT_LAST_NAME)
            .email(DEFAULT_EMAIL)
            .phoneNumber(DEFAULT_PHONE_NUMBER)
            .hireDate(DEFAULT_HIRE_DATE);
        return fdcUser;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FdcUser createUpdatedEntity(EntityManager em) {
        FdcUser fdcUser = new FdcUser()
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .email(UPDATED_EMAIL)
            .phoneNumber(UPDATED_PHONE_NUMBER)
            .hireDate(UPDATED_HIRE_DATE);
        return fdcUser;
    }

    @BeforeEach
    public void initTest() {
        fdcUser = createEntity(em);
    }

    @Test
    @Transactional
    public void createFdcUser() throws Exception {
        int databaseSizeBeforeCreate = fdcUserRepository.findAll().size();
        // Create the FdcUser
        restFdcUserMockMvc.perform(post("/api/fdc-users")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(fdcUser)))
            .andExpect(status().isCreated());

        // Validate the FdcUser in the database
        List<FdcUser> fdcUserList = fdcUserRepository.findAll();
        assertThat(fdcUserList).hasSize(databaseSizeBeforeCreate + 1);
        FdcUser testFdcUser = fdcUserList.get(fdcUserList.size() - 1);
        assertThat(testFdcUser.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testFdcUser.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testFdcUser.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testFdcUser.getPhoneNumber()).isEqualTo(DEFAULT_PHONE_NUMBER);
        assertThat(testFdcUser.getHireDate()).isEqualTo(DEFAULT_HIRE_DATE);
    }

    @Test
    @Transactional
    public void createFdcUserWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = fdcUserRepository.findAll().size();

        // Create the FdcUser with an existing ID
        fdcUser.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFdcUserMockMvc.perform(post("/api/fdc-users")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(fdcUser)))
            .andExpect(status().isBadRequest());

        // Validate the FdcUser in the database
        List<FdcUser> fdcUserList = fdcUserRepository.findAll();
        assertThat(fdcUserList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllFdcUsers() throws Exception {
        // Initialize the database
        fdcUserRepository.saveAndFlush(fdcUser);

        // Get all the fdcUserList
        restFdcUserMockMvc.perform(get("/api/fdc-users?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(fdcUser.getId().intValue())))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME)))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].phoneNumber").value(hasItem(DEFAULT_PHONE_NUMBER)))
            .andExpect(jsonPath("$.[*].hireDate").value(hasItem(DEFAULT_HIRE_DATE.toString())));
    }
    
    @Test
    @Transactional
    public void getFdcUser() throws Exception {
        // Initialize the database
        fdcUserRepository.saveAndFlush(fdcUser);

        // Get the fdcUser
        restFdcUserMockMvc.perform(get("/api/fdc-users/{id}", fdcUser.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(fdcUser.getId().intValue()))
            .andExpect(jsonPath("$.firstName").value(DEFAULT_FIRST_NAME))
            .andExpect(jsonPath("$.lastName").value(DEFAULT_LAST_NAME))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.phoneNumber").value(DEFAULT_PHONE_NUMBER))
            .andExpect(jsonPath("$.hireDate").value(DEFAULT_HIRE_DATE.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingFdcUser() throws Exception {
        // Get the fdcUser
        restFdcUserMockMvc.perform(get("/api/fdc-users/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFdcUser() throws Exception {
        // Initialize the database
        fdcUserRepository.saveAndFlush(fdcUser);

        int databaseSizeBeforeUpdate = fdcUserRepository.findAll().size();

        // Update the fdcUser
        FdcUser updatedFdcUser = fdcUserRepository.findById(fdcUser.getId()).get();
        // Disconnect from session so that the updates on updatedFdcUser are not directly saved in db
        em.detach(updatedFdcUser);
        updatedFdcUser
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .email(UPDATED_EMAIL)
            .phoneNumber(UPDATED_PHONE_NUMBER)
            .hireDate(UPDATED_HIRE_DATE);

        restFdcUserMockMvc.perform(put("/api/fdc-users")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedFdcUser)))
            .andExpect(status().isOk());

        // Validate the FdcUser in the database
        List<FdcUser> fdcUserList = fdcUserRepository.findAll();
        assertThat(fdcUserList).hasSize(databaseSizeBeforeUpdate);
        FdcUser testFdcUser = fdcUserList.get(fdcUserList.size() - 1);
        assertThat(testFdcUser.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testFdcUser.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testFdcUser.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testFdcUser.getPhoneNumber()).isEqualTo(UPDATED_PHONE_NUMBER);
        assertThat(testFdcUser.getHireDate()).isEqualTo(UPDATED_HIRE_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingFdcUser() throws Exception {
        int databaseSizeBeforeUpdate = fdcUserRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFdcUserMockMvc.perform(put("/api/fdc-users")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(fdcUser)))
            .andExpect(status().isBadRequest());

        // Validate the FdcUser in the database
        List<FdcUser> fdcUserList = fdcUserRepository.findAll();
        assertThat(fdcUserList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteFdcUser() throws Exception {
        // Initialize the database
        fdcUserRepository.saveAndFlush(fdcUser);

        int databaseSizeBeforeDelete = fdcUserRepository.findAll().size();

        // Delete the fdcUser
        restFdcUserMockMvc.perform(delete("/api/fdc-users/{id}", fdcUser.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<FdcUser> fdcUserList = fdcUserRepository.findAll();
        assertThat(fdcUserList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
