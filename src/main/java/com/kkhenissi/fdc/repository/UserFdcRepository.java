package com.kkhenissi.fdc.repository;

import com.kkhenissi.fdc.domain.UserFdc;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the UserFdc entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UserFdcRepository extends JpaRepository<UserFdc, Long> {
}
