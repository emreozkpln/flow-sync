package dev.buddly.flow_sync.service;


import dev.buddly.flow_sync.dto.StoreDto;

import java.util.List;

public interface StoreService {
    List<StoreDto> getAllStores();

    StoreDto findById(Integer id);
    List<StoreDto> findByStoreId(String storeId);
    List<StoreDto> findByStoreName(String storeName);
    List<StoreDto> findByStoreLat(String lat);
    List<StoreDto> findByStoreLng(String lng);
    void addStore(StoreDto storeDto);
    StoreDto updateStore(StoreDto storeDto, Integer id);
    void deleteStore(Integer id);
}
