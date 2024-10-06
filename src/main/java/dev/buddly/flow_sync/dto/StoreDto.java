package dev.buddly.flow_sync.dto;

import dev.buddly.flow_sync.model.Product;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@RequiredArgsConstructor
public class StoreDto {
    private Integer id;
    @NotEmpty(message = "Store name cannot be empty")
    private String storeName;
    @NotEmpty(message = "Store id cannot be empty")
    private String storeId;
    @NotEmpty(message = "Lat cannot be empty")
    private String lat;
    @NotEmpty(message = "Lng cannot be empty")
    private String lng;
    private List<Product> products;
}
