package com.kkhenissi.fdc.service;

import com.kkhenissi.fdc.domain.Photo;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Photo}.
 */
public interface PhotoService {

    /**
     * Save a photo.
     *
     * @param photo the entity to save.
     * @return the persisted entity.
     */
    Photo save(Photo photo);

    /**
     * Get all the photos.
     *
     * @return the list of entities.
     */
    List<Photo> findAll();


    /**
     * Get the "id" photo.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Photo> findOne(Long id);

    /**
     * Delete the "id" photo.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
