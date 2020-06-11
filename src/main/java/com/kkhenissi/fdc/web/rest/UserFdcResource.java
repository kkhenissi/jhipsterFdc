package com.kkhenissi.fdc.web.rest;

import com.kkhenissi.fdc.domain.UserFdc;
import com.kkhenissi.fdc.service.UserFdcService;
import com.kkhenissi.fdc.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.kkhenissi.fdc.domain.UserFdc}.
 */
@RestController
@RequestMapping("/api")
public class UserFdcResource {

    private final Logger log = LoggerFactory.getLogger(UserFdcResource.class);

    private static final String ENTITY_NAME = "userFdc";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final UserFdcService userFdcService;

    public UserFdcResource(UserFdcService userFdcService) {
        this.userFdcService = userFdcService;
    }

    /**
     * {@code POST  /user-fdcs} : Create a new userFdc.
     *
     * @param userFdc the userFdc to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new userFdc, or with status {@code 400 (Bad Request)} if the userFdc has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/user-fdcs")
    public ResponseEntity<UserFdc> createUserFdc(@RequestBody UserFdc userFdc) throws URISyntaxException {
        log.debug("REST request to save UserFdc : {}", userFdc);
        if (userFdc.getId() != null) {
            throw new BadRequestAlertException("A new userFdc cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UserFdc result = userFdcService.save(userFdc);
        return ResponseEntity.created(new URI("/api/user-fdcs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /user-fdcs} : Updates an existing userFdc.
     *
     * @param userFdc the userFdc to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated userFdc,
     * or with status {@code 400 (Bad Request)} if the userFdc is not valid,
     * or with status {@code 500 (Internal Server Error)} if the userFdc couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/user-fdcs")
    public ResponseEntity<UserFdc> updateUserFdc(@RequestBody UserFdc userFdc) throws URISyntaxException {
        log.debug("REST request to update UserFdc : {}", userFdc);
        if (userFdc.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        UserFdc result = userFdcService.save(userFdc);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, userFdc.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /user-fdcs} : get all the userFdcs.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of userFdcs in body.
     */
    @GetMapping("/user-fdcs")
    public List<UserFdc> getAllUserFdcs() {
        log.debug("REST request to get all UserFdcs");
        return userFdcService.findAll();
    }

    /**
     * {@code GET  /user-fdcs/:id} : get the "id" userFdc.
     *
     * @param id the id of the userFdc to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the userFdc, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/user-fdcs/{id}")
    public ResponseEntity<UserFdc> getUserFdc(@PathVariable Long id) {
        log.debug("REST request to get UserFdc : {}", id);
        Optional<UserFdc> userFdc = userFdcService.findOne(id);
        return ResponseUtil.wrapOrNotFound(userFdc);
    }

    /**
     * {@code DELETE  /user-fdcs/:id} : delete the "id" userFdc.
     *
     * @param id the id of the userFdc to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/user-fdcs/{id}")
    public ResponseEntity<Void> deleteUserFdc(@PathVariable Long id) {
        log.debug("REST request to delete UserFdc : {}", id);
        userFdcService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
