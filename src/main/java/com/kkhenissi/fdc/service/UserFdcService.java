package com.kkhenissi.fdc.service;

import com.kkhenissi.fdc.domain.UserFdc;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link UserFdc}.
 */
public interface UserFdcService {

    /**
     * Save a userFdc.
     *
     * @param userFdc the entity to save.
     * @return the persisted entity.
     */
    UserFdc save(UserFdc userFdc);

    /**
     * Get all the userFdcs.
     *
     * @return the list of entities.
     */
    List<UserFdc> findAll();


    /**
     * Get the "id" userFdc.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<UserFdc> findOne(Long id);

    /**
     * Delete the "id" userFdc.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
