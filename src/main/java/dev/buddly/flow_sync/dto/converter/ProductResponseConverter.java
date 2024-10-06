package dev.buddly.flow_sync.dto.converter;

import dev.buddly.flow_sync.dto.ProductResponse;
import dev.buddly.flow_sync.model.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductResponseConverter {

    public ProductResponse convert(Product product){
        return ProductResponse.builder()
                .id(product.getId())
                .productId(product.getProductId())
                .productName(product.getProductName())
                .stat(product.getStat())
                .storeId(product.getStore().getStoreId())
                .batteryLevel(product.getBatteryLevel())
                .passingTime(product.getPassingTime())
                .readingTime(String.valueOf(product.getReadingTime()))
                .build();
    }
}
