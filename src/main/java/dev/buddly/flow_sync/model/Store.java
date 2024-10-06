package dev.buddly.flow_sync.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Store {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String storeName;

    private String storeId;

    private String lat;

    private String lng;

    @OneToMany(
            mappedBy = "store",
            cascade = CascadeType.ALL
    )
    @JsonManagedReference
    private List<Product> products;
}
