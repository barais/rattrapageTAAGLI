package com.eit.vipo.repository;

import com.eit.vipo.domain.VipoEntry;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data repository for the VipoEntry entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VipoEntryRepository extends JpaRepository<VipoEntry, Long> {
    @Query("select v.entries from Vipo v where v.user.login = :login")
    Page<VipoEntry> getAllVipoEntriesByVipo(Pageable pageable, @Param("login") String login);

    @Query("select v.entries from Vipo v where v.store.user.login = :login")
    Page<VipoEntry> getAllVipoEntriesByClient(Pageable pageable, @Param("login") String login);

}
