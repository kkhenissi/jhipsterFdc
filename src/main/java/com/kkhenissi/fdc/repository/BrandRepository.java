package com.kkhenissi.fdc.repository;

import com.kkhenissi.fdc.domain.Brand;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the Brand entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BrandRepository extends JpaRepository<Brand, Long> {

    @Query("select brand from Brand brand where brand.user.login = ?#{principal.username}")
    List<Brand> findByUserIsCurrentUser();
}
