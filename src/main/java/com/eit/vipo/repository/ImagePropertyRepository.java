package com.eit.vipo.repository;

import com.eit.vipo.domain.ImageProperty;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data repository for the ImageProperty entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ImagePropertyRepository extends JpaRepository<ImageProperty, Long> {

    @Query("select v.props from VipoEntry v where v.vipo.user.login = :login")
    Page<ImageProperty> getAllImagePropertiesByVipo(Pageable pageable, @Param("login") String login);

    @Query("select v.props from VipoEntry v where v.vipo.store.user.login = :login")
    Page<ImageProperty> getAllImagePropertiesByClient(Pageable pageable, @Param("login") String login);

}
