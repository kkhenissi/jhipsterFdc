package com.kkhenissi.fdc.service.impl;

import com.kkhenissi.fdc.service.UserFdcService;
import com.kkhenissi.fdc.domain.UserFdc;
import com.kkhenissi.fdc.repository.UserFdcRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link UserFdc}.
 */
@Service
@Transactional
public class UserFdcServiceImpl implements UserFdcService {

    private final Logger log = LoggerFactory.getLogger(UserFdcServiceImpl.class);

    private final UserFdcRepository userFdcRepository;

    public UserFdcServiceImpl(UserFdcRepository userFdcRepository) {
        this.userFdcRepository = userFdcRepository;
    }

    /**
     * Save a userFdc.
     *
     * @param userFdc the entity to save.
     * @return the persisted entity.
     */
    @Override
    public UserFdc save(UserFdc userFdc) {
        log.debug("Request to save UserFdc : {}", userFdc);
        return userFdcRepository.save(userFdc);
    }

    /**
     * Get all the userFdcs.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<UserFdc> findAll() {
        log.debug("Request to get all UserFdcs");
        return userFdcRepository.findAll();
    }


    /**
     * Get one userFdc by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<UserFdc> findOne(Long id) {
        log.debug("Request to get UserFdc : {}", id);
        return userFdcRepository.findById(id);
    }

    /**
     * Delete the userFdc by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete UserFdc : {}", id);
        userFdcRepository.deleteById(id);
    }
}
