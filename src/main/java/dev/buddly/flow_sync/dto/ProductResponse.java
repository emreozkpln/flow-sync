package dev.buddly.flow_sync.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductResponse {
    private Integer id;
    private String productId;
    private String productName;
    private String stat;
    private String readingTime;
    private String passingTime;
    private String batteryLevel;
    private String storeId;
}
