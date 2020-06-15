package com.eit.vipo.repository;

import java.util.Optional;

import com.eit.vipo.domain.User;
import com.eit.vipo.domain.Vipo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data repository for the Vipo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VipoRepository extends JpaRepository<Vipo, Long> {

    @Query("select v from Vipo v where v.user.login = :login")
    Page<Vipo> getAllViposByVipo(Pageable pageable, @Param("login") String login);

    @Query("select v from Vipo v where v.store.user.login = :login")
    Page<Vipo> getAllViposByClient(Pageable pageable, @Param("login") String login);

    @Query("select v from Vipo v where v.user.login = :login")
    Optional<Vipo> findOneByLogin(@Param("login") String login);

    @Query("select v from Vipo v where v.store.user.login = :login")
    Optional<Vipo> findOneByLogin4Client(@Param("login") String login);

}
