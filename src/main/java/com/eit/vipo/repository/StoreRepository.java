package com.eit.vipo.repository;

import com.eit.vipo.domain.Store;
import com.eit.vipo.domain.VipoEntry;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data repository for the Store entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StoreRepository extends JpaRepository<Store, Long> {

    @Query("select v.store from Vipo v where v.user.login = :login")
    Page<Store> getAllStoresByVipo(Pageable pageable, @Param("login") String login);

    @Query("select v.store from Vipo v where v.store.user.login = :login")
    Page<Store> getAllStoresByClient(Pageable pageable, @Param("login") String login);

}
