package dev.buddly.flow_sync.repository;

import dev.buddly.flow_sync.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product,Integer> {

    @Override
    Optional<Product> findById(Integer id);

    List<Product> findAllByProductIdContaining(String productId);
    List<Product> findAllByProductNameContaining(String productName);
    List<Product> findAllByStatContaining(String stat);
    List<Product> findAllByBatteryLevelContaining(String batteryLevel);
    List<Product> findAllByStore_StoreIdContaining(String storeId);
    Product findByProductId(String productId);
    Product findByProductName(String productName);
}
