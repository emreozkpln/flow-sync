package dev.buddly.flow_sync.service.impl;

import dev.buddly.flow_sync.dto.StoreDto;
import dev.buddly.flow_sync.exception.AlreadyExistsException;
import dev.buddly.flow_sync.exception.NotFoundException;
import dev.buddly.flow_sync.model.Store;
import dev.buddly.flow_sync.repository.StoreRepository;
import dev.buddly.flow_sync.service.StoreService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class StoreServiceImpl implements StoreService {

    private final StoreRepository storeRepository;
    private final ModelMapper modelMapper;

    public StoreServiceImpl(StoreRepository storeRepository, ModelMapper modelMapper) {
        this.storeRepository = storeRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<StoreDto> getAllStores() {
        List<Store> stores = storeRepository.findAll();
        return stores.stream()
                .map(store->modelMapper.map(store,StoreDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public StoreDto findById(Integer id) {
        Store store  = storeRepository.findById(id)
                .orElseThrow(()->new NotFoundException("Store not found"));
        return modelMapper.map(store,StoreDto.class);
    }

    @Override
    public List<StoreDto> findByStoreId(String storeId) {
        List<Store> stores = storeRepository.findAllByStoreIdContaining(storeId);
        return stores.stream()
                .filter(item->item.getStoreId().contains(storeId))
                .map(store->modelMapper.map(store,StoreDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<StoreDto> findByStoreName(String storeName) {
        List<Store> stores = storeRepository.findAllByStoreNameContaining(storeName);
        return stores.stream()
                .filter(item->item.getStoreName().contains(storeName))
                .map(store->modelMapper.map(store,StoreDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<StoreDto> findByStoreLat(String lat) {
        List<Store> stores = storeRepository.findAllByLatContaining(lat);
        return stores.stream()
                .filter(item->item.getLat().contains(lat))
                .map(store->modelMapper.map(store,StoreDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<StoreDto> findByStoreLng(String lng) {
        List<Store> stores = storeRepository.findAllByLngContaining(lng);
        return stores.stream()
                .filter(item->item.getLng().contains(lng))
                .map(store->modelMapper.map(store,StoreDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public void addStore(StoreDto storeDto) {
        var existingStoreByStoreId = storeRepository.findByStoreId(storeDto.getStoreId());
        var existingStoreByStoreName = storeRepository.findStoreByStoreName(storeDto.getStoreName());
        if(existingStoreByStoreId != null){
            log.error("store id already have");
            throw new AlreadyExistsException("store id already have");
        }
        if(existingStoreByStoreName !=null){
            log.error("store name already have");
            throw new AlreadyExistsException("store name already have");
        }
        var store = modelMapper.map(storeDto,Store.class);
        var savedStore = storeRepository.save(store);
        modelMapper.map(savedStore,StoreDto.class);
    }

    @Override
    public StoreDto updateStore(StoreDto storeDto, Integer id) {
        Store store = storeRepository.findById(id)
                .orElseThrow(()->new NotFoundException("store id not found"));
        store.setStoreId(storeDto.getStoreId());
        store.setStoreName(storeDto.getStoreName());
        store.setLng(storeDto.getLng());
        store.setLat(storeDto.getLat());
        Store updatedStore = storeRepository.save(store);
        return modelMapper.map(updatedStore,StoreDto.class);
    }

    @Override
    public void deleteStore(Integer id) {
        Store store  = storeRepository.findById(id)
                .orElseThrow(()->new NotFoundException("store id not found"));
        storeRepository.delete(store);
    }
}
