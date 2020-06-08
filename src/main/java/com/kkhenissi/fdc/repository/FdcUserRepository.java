package com.kkhenissi.fdc.repository;

import com.kkhenissi.fdc.domain.FdcUser;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the FdcUser entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FdcUserRepository extends JpaRepository<FdcUser, Long> {
}
