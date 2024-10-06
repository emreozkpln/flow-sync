package dev.buddly.flow_sync.controller;

import dev.buddly.flow_sync.dto.ResponseHandler;
import dev.buddly.flow_sync.dto.StoreDto;
import dev.buddly.flow_sync.service.StoreService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/store")
@Tag(name = "Store")
public class StoreController {

    private final StoreService storeService;

    public StoreController(StoreService storeService) {
        this.storeService = storeService;
    }

    @GetMapping
    public ResponseEntity<List<StoreDto>> getAllStores(){
        List<StoreDto> stores = storeService.getAllStores();
        return ResponseEntity.ok(stores);
    }
    @GetMapping("/id/{id}")
    public ResponseEntity<StoreDto> getStoreById(@PathVariable Integer id){
        StoreDto storeDto = storeService.findById(id);
        return ResponseEntity.ok(storeDto);
    }
    @GetMapping("/storeId/{storeId}")
    public ResponseEntity<List<StoreDto>> getByStoreId(@PathVariable String storeId){
        List<StoreDto> storeDto = storeService.findByStoreId(storeId);
        return ResponseEntity.ok(storeDto);
    }
    @GetMapping("/storeName/{storeName}")
    public ResponseEntity<List<StoreDto>> getByStoreName(@PathVariable String storeName){
        List<StoreDto> storeDto = storeService.findByStoreName(storeName);
        return ResponseEntity.ok(storeDto);
    }
    @GetMapping("/Lat/{lat}")
    public ResponseEntity<List<StoreDto>> getByStoreLat(@PathVariable String lat){
        List<StoreDto> storeDto = storeService.findByStoreLat(lat);
        return ResponseEntity.ok(storeDto);
    }
    @GetMapping("/Lng/{lng}")
    public ResponseEntity<List<StoreDto>> getByStoreLng(@PathVariable String lng){
        List<StoreDto> storeDto = storeService.findByStoreLng(lng);
        return ResponseEntity.ok(storeDto);
    }
    @PostMapping
    public ResponseEntity<Object> addStore(@Valid @RequestBody StoreDto storeDto){
        storeService.addStore(storeDto);
        return ResponseHandler.responseBuilder("Store added successfully",HttpStatus.CREATED,storeDto);
    }
    @PutMapping("/store/{id}")
    public ResponseEntity<StoreDto> updateStore(@RequestBody StoreDto storeDto, @PathVariable Integer id){
        StoreDto dto = storeService.updateStore(storeDto,id);
        return ResponseEntity.ok(dto);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteStore(@PathVariable Integer id){
        storeService.deleteStore(id);
        return ResponseEntity.ok("Deleted successfully");
    }
}
