package dev.buddly.flow_sync.service;


import dev.buddly.flow_sync.dto.ProductRequest;
import dev.buddly.flow_sync.dto.ProductResponse;

import java.util.List;

public interface ProductService {

    List<ProductResponse> getAllProducts();
    ProductResponse findById(Integer id);
    List<ProductResponse> findByProductId(String productId);
    List<ProductResponse> findByStoreId(String storeId);
    List<ProductResponse> findByProductName(String productName);
    List<ProductResponse> findByStat(String stat);
    List<ProductResponse> findByBatteryLevel(String batteryLevel);
    ProductRequest addProduct(ProductRequest productDto);
    ProductRequest updateProduct(ProductRequest productDto, Integer id);
    void deleteProduct(Integer id);
}
