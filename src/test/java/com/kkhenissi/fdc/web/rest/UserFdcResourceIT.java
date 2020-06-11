package com.kkhenissi.fdc.web.rest;

import com.kkhenissi.fdc.JhipsterFdcApp;
import com.kkhenissi.fdc.domain.UserFdc;
import com.kkhenissi.fdc.repository.UserFdcRepository;
import com.kkhenissi.fdc.service.UserFdcService;

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
 * Integration tests for the {@link UserFdcResource} REST controller.
 */
@SpringBootTest(classes = JhipsterFdcApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class UserFdcResourceIT {

    private static final String DEFAULT_USER_NAME_USER = "AAAAAAAAAA";
    private static final String UPDATED_USER_NAME_USER = "BBBBBBBBBB";

    private static final String DEFAULT_FIRST_NAME_USER = "AAAAAAAAAA";
    private static final String UPDATED_FIRST_NAME_USER = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_NAME_USER = "AAAAAAAAAA";
    private static final String UPDATED_LAST_NAME_USER = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL_USER = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL_USER = "BBBBBBBBBB";

    private static final String DEFAULT_PASSWORD_USER = "AAAAAAAAAA";
    private static final String UPDATED_PASSWORD_USER = "BBBBBBBBBB";

    private static final Instant DEFAULT_DATE_REGISTRATION_USER = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE_REGISTRATION_USER = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_AVATAR_USER = "AAAAAAAAAA";
    private static final String UPDATED_AVATAR_USER = "BBBBBBBBBB";

    private static final Boolean DEFAULT_STATUS_USER = false;
    private static final Boolean UPDATED_STATUS_USER = true;

    @Autowired
    private UserFdcRepository userFdcRepository;

    @Autowired
    private UserFdcService userFdcService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restUserFdcMockMvc;

    private UserFdc userFdc;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserFdc createEntity(EntityManager em) {
        UserFdc userFdc = new UserFdc()
            .userNameUser(DEFAULT_USER_NAME_USER)
            .firstNameUser(DEFAULT_FIRST_NAME_USER)
            .lastNameUser(DEFAULT_LAST_NAME_USER)
            .emailUser(DEFAULT_EMAIL_USER)
            .passwordUser(DEFAULT_PASSWORD_USER)
            .dateRegistrationUser(DEFAULT_DATE_REGISTRATION_USER)
            .avatarUser(DEFAULT_AVATAR_USER)
            .statusUser(DEFAULT_STATUS_USER);
        return userFdc;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserFdc createUpdatedEntity(EntityManager em) {
        UserFdc userFdc = new UserFdc()
            .userNameUser(UPDATED_USER_NAME_USER)
            .firstNameUser(UPDATED_FIRST_NAME_USER)
            .lastNameUser(UPDATED_LAST_NAME_USER)
            .emailUser(UPDATED_EMAIL_USER)
            .passwordUser(UPDATED_PASSWORD_USER)
            .dateRegistrationUser(UPDATED_DATE_REGISTRATION_USER)
            .avatarUser(UPDATED_AVATAR_USER)
            .statusUser(UPDATED_STATUS_USER);
        return userFdc;
    }

    @BeforeEach
    public void initTest() {
        userFdc = createEntity(em);
    }

    @Test
    @Transactional
    public void createUserFdc() throws Exception {
        int databaseSizeBeforeCreate = userFdcRepository.findAll().size();
        // Create the UserFdc
        restUserFdcMockMvc.perform(post("/api/user-fdcs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userFdc)))
            .andExpect(status().isCreated());

        // Validate the UserFdc in the database
        List<UserFdc> userFdcList = userFdcRepository.findAll();
        assertThat(userFdcList).hasSize(databaseSizeBeforeCreate + 1);
        UserFdc testUserFdc = userFdcList.get(userFdcList.size() - 1);
        assertThat(testUserFdc.getUserNameUser()).isEqualTo(DEFAULT_USER_NAME_USER);
        assertThat(testUserFdc.getFirstNameUser()).isEqualTo(DEFAULT_FIRST_NAME_USER);
        assertThat(testUserFdc.getLastNameUser()).isEqualTo(DEFAULT_LAST_NAME_USER);
        assertThat(testUserFdc.getEmailUser()).isEqualTo(DEFAULT_EMAIL_USER);
        assertThat(testUserFdc.getPasswordUser()).isEqualTo(DEFAULT_PASSWORD_USER);
        assertThat(testUserFdc.getDateRegistrationUser()).isEqualTo(DEFAULT_DATE_REGISTRATION_USER);
        assertThat(testUserFdc.getAvatarUser()).isEqualTo(DEFAULT_AVATAR_USER);
        assertThat(testUserFdc.isStatusUser()).isEqualTo(DEFAULT_STATUS_USER);
    }

    @Test
    @Transactional
    public void createUserFdcWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = userFdcRepository.findAll().size();

        // Create the UserFdc with an existing ID
        userFdc.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUserFdcMockMvc.perform(post("/api/user-fdcs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userFdc)))
            .andExpect(status().isBadRequest());

        // Validate the UserFdc in the database
        List<UserFdc> userFdcList = userFdcRepository.findAll();
        assertThat(userFdcList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllUserFdcs() throws Exception {
        // Initialize the database
        userFdcRepository.saveAndFlush(userFdc);

        // Get all the userFdcList
        restUserFdcMockMvc.perform(get("/api/user-fdcs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userFdc.getId().intValue())))
            .andExpect(jsonPath("$.[*].userNameUser").value(hasItem(DEFAULT_USER_NAME_USER)))
            .andExpect(jsonPath("$.[*].firstNameUser").value(hasItem(DEFAULT_FIRST_NAME_USER)))
            .andExpect(jsonPath("$.[*].lastNameUser").value(hasItem(DEFAULT_LAST_NAME_USER)))
            .andExpect(jsonPath("$.[*].emailUser").value(hasItem(DEFAULT_EMAIL_USER)))
            .andExpect(jsonPath("$.[*].passwordUser").value(hasItem(DEFAULT_PASSWORD_USER)))
            .andExpect(jsonPath("$.[*].dateRegistrationUser").value(hasItem(DEFAULT_DATE_REGISTRATION_USER.toString())))
            .andExpect(jsonPath("$.[*].avatarUser").value(hasItem(DEFAULT_AVATAR_USER)))
            .andExpect(jsonPath("$.[*].statusUser").value(hasItem(DEFAULT_STATUS_USER.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getUserFdc() throws Exception {
        // Initialize the database
        userFdcRepository.saveAndFlush(userFdc);

        // Get the userFdc
        restUserFdcMockMvc.perform(get("/api/user-fdcs/{id}", userFdc.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(userFdc.getId().intValue()))
            .andExpect(jsonPath("$.userNameUser").value(DEFAULT_USER_NAME_USER))
            .andExpect(jsonPath("$.firstNameUser").value(DEFAULT_FIRST_NAME_USER))
            .andExpect(jsonPath("$.lastNameUser").value(DEFAULT_LAST_NAME_USER))
            .andExpect(jsonPath("$.emailUser").value(DEFAULT_EMAIL_USER))
            .andExpect(jsonPath("$.passwordUser").value(DEFAULT_PASSWORD_USER))
            .andExpect(jsonPath("$.dateRegistrationUser").value(DEFAULT_DATE_REGISTRATION_USER.toString()))
            .andExpect(jsonPath("$.avatarUser").value(DEFAULT_AVATAR_USER))
            .andExpect(jsonPath("$.statusUser").value(DEFAULT_STATUS_USER.booleanValue()));
    }
    @Test
    @Transactional
    public void getNonExistingUserFdc() throws Exception {
        // Get the userFdc
        restUserFdcMockMvc.perform(get("/api/user-fdcs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUserFdc() throws Exception {
        // Initialize the database
        userFdcService.save(userFdc);

        int databaseSizeBeforeUpdate = userFdcRepository.findAll().size();

        // Update the userFdc
        UserFdc updatedUserFdc = userFdcRepository.findById(userFdc.getId()).get();
        // Disconnect from session so that the updates on updatedUserFdc are not directly saved in db
        em.detach(updatedUserFdc);
        updatedUserFdc
            .userNameUser(UPDATED_USER_NAME_USER)
            .firstNameUser(UPDATED_FIRST_NAME_USER)
            .lastNameUser(UPDATED_LAST_NAME_USER)
            .emailUser(UPDATED_EMAIL_USER)
            .passwordUser(UPDATED_PASSWORD_USER)
            .dateRegistrationUser(UPDATED_DATE_REGISTRATION_USER)
            .avatarUser(UPDATED_AVATAR_USER)
            .statusUser(UPDATED_STATUS_USER);

        restUserFdcMockMvc.perform(put("/api/user-fdcs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedUserFdc)))
            .andExpect(status().isOk());

        // Validate the UserFdc in the database
        List<UserFdc> userFdcList = userFdcRepository.findAll();
        assertThat(userFdcList).hasSize(databaseSizeBeforeUpdate);
        UserFdc testUserFdc = userFdcList.get(userFdcList.size() - 1);
        assertThat(testUserFdc.getUserNameUser()).isEqualTo(UPDATED_USER_NAME_USER);
        assertThat(testUserFdc.getFirstNameUser()).isEqualTo(UPDATED_FIRST_NAME_USER);
        assertThat(testUserFdc.getLastNameUser()).isEqualTo(UPDATED_LAST_NAME_USER);
        assertThat(testUserFdc.getEmailUser()).isEqualTo(UPDATED_EMAIL_USER);
        assertThat(testUserFdc.getPasswordUser()).isEqualTo(UPDATED_PASSWORD_USER);
        assertThat(testUserFdc.getDateRegistrationUser()).isEqualTo(UPDATED_DATE_REGISTRATION_USER);
        assertThat(testUserFdc.getAvatarUser()).isEqualTo(UPDATED_AVATAR_USER);
        assertThat(testUserFdc.isStatusUser()).isEqualTo(UPDATED_STATUS_USER);
    }

    @Test
    @Transactional
    public void updateNonExistingUserFdc() throws Exception {
        int databaseSizeBeforeUpdate = userFdcRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUserFdcMockMvc.perform(put("/api/user-fdcs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userFdc)))
            .andExpect(status().isBadRequest());

        // Validate the UserFdc in the database
        List<UserFdc> userFdcList = userFdcRepository.findAll();
        assertThat(userFdcList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteUserFdc() throws Exception {
        // Initialize the database
        userFdcService.save(userFdc);

        int databaseSizeBeforeDelete = userFdcRepository.findAll().size();

        // Delete the userFdc
        restUserFdcMockMvc.perform(delete("/api/user-fdcs/{id}", userFdc.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<UserFdc> userFdcList = userFdcRepository.findAll();
        assertThat(userFdcList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
