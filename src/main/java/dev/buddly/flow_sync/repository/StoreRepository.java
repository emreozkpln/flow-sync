package dev.buddly.flow_sync.repository;

import dev.buddly.flow_sync.model.Store;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StoreRepository extends JpaRepository<Store,Integer> {

    Optional<Store> findById(Integer id);
    List<Store> findAllByStoreIdContaining(String storeId);
    List<Store> findAllByStoreNameContaining(String storeName);
    List<Store> findAllByLatContaining(String lat);
    List<Store> findAllByLngContaining(String lng);
    Store findByStoreId(String storeId);
    Store findStoreByStoreName(String storeName);
}
