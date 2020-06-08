package com.kkhenissi.fdc.web.rest;

import com.kkhenissi.fdc.domain.FdcUser;
import com.kkhenissi.fdc.repository.FdcUserRepository;
import com.kkhenissi.fdc.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.kkhenissi.fdc.domain.FdcUser}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class FdcUserResource {

    private final Logger log = LoggerFactory.getLogger(FdcUserResource.class);

    private static final String ENTITY_NAME = "fdcUser";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FdcUserRepository fdcUserRepository;

    public FdcUserResource(FdcUserRepository fdcUserRepository) {
        this.fdcUserRepository = fdcUserRepository;
    }

    /**
     * {@code POST  /fdc-users} : Create a new fdcUser.
     *
     * @param fdcUser the fdcUser to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new fdcUser, or with status {@code 400 (Bad Request)} if the fdcUser has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/fdc-users")
    public ResponseEntity<FdcUser> createFdcUser(@RequestBody FdcUser fdcUser) throws URISyntaxException {
        log.debug("REST request to save FdcUser : {}", fdcUser);
        if (fdcUser.getId() != null) {
            throw new BadRequestAlertException("A new fdcUser cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FdcUser result = fdcUserRepository.save(fdcUser);
        return ResponseEntity.created(new URI("/api/fdc-users/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /fdc-users} : Updates an existing fdcUser.
     *
     * @param fdcUser the fdcUser to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated fdcUser,
     * or with status {@code 400 (Bad Request)} if the fdcUser is not valid,
     * or with status {@code 500 (Internal Server Error)} if the fdcUser couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/fdc-users")
    public ResponseEntity<FdcUser> updateFdcUser(@RequestBody FdcUser fdcUser) throws URISyntaxException {
        log.debug("REST request to update FdcUser : {}", fdcUser);
        if (fdcUser.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        FdcUser result = fdcUserRepository.save(fdcUser);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, fdcUser.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /fdc-users} : get all the fdcUsers.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of fdcUsers in body.
     */
    @GetMapping("/fdc-users")
    public ResponseEntity<List<FdcUser>> getAllFdcUsers(Pageable pageable) {
        log.debug("REST request to get a page of FdcUsers");
        Page<FdcUser> page = fdcUserRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /fdc-users/:id} : get the "id" fdcUser.
     *
     * @param id the id of the fdcUser to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the fdcUser, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/fdc-users/{id}")
    public ResponseEntity<FdcUser> getFdcUser(@PathVariable Long id) {
        log.debug("REST request to get FdcUser : {}", id);
        Optional<FdcUser> fdcUser = fdcUserRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(fdcUser);
    }

    /**
     * {@code DELETE  /fdc-users/:id} : delete the "id" fdcUser.
     *
     * @param id the id of the fdcUser to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/fdc-users/{id}")
    public ResponseEntity<Void> deleteFdcUser(@PathVariable Long id) {
        log.debug("REST request to delete FdcUser : {}", id);
        fdcUserRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
