package dev.buddly.flow_sync.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true)
    private String productId;

    private String productName;

    private String stat;

    private LocalDateTime readingTime;

    private String passingTime;

    private String batteryLevel;

    @ManyToOne
    @JoinColumn(
        name = "store_id"
    )
    @JsonBackReference
    private Store store;

}
