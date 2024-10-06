package dev.buddly.flow_sync.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequest {
    private Integer id;
    @NotEmpty(message = "Product id cannot be empty")
    private String productId;
    private String productName;
    private String stat;
    private String readingTime;
    private String passingTime;
    private String batteryLevel;
    private Integer store_id;
}
