package dev.buddly.flow_sync.controller;

import dev.buddly.flow_sync.dto.ProductRequest;
import dev.buddly.flow_sync.dto.ProductResponse;
import dev.buddly.flow_sync.service.ProductService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/product")
@Tag(name = "Product")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<List<ProductResponse>> getAllProducts(){
        List<ProductResponse> products = productService.getAllProducts();
        return ResponseEntity.ok(products);
    }
    @GetMapping("/id/{id}")
    public ResponseEntity<ProductResponse> getById(@PathVariable Integer id){
        ProductResponse product = productService.findById(id);
        return ResponseEntity.ok(product);
    }
    @GetMapping("/productId/{storeId}")
    public ResponseEntity<List<ProductResponse>> getByStoreId(@PathVariable String storeId){
        List<ProductResponse> products = productService.findByStoreId(storeId);
        return ResponseEntity.ok(products);
    }
    @GetMapping("/productName/{productName}")
    public ResponseEntity<List<ProductResponse>> getByProductName(@PathVariable String productName){
        List<ProductResponse> products = productService.findByProductName(productName);
        return ResponseEntity.ok(products);
    }
    @GetMapping("/stat/{stat}")
    public ResponseEntity<List<ProductResponse>> getByStat(@PathVariable String stat){
        List<ProductResponse> products = productService.findByStat(stat);
        return ResponseEntity.ok(products);
    }
    @GetMapping("/batteryLevel/{batteryLevel}")
    public ResponseEntity<List<ProductResponse>> getByBatteryLevel(@PathVariable String batteryLevel){
        List<ProductResponse> products = productService.findByBatteryLevel(batteryLevel);
        return ResponseEntity.ok(products);
    }
    @PostMapping
    public ResponseEntity<String> addProduct(@Valid @RequestBody ProductRequest productRequest){
        productService.addProduct(productRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body("Added successfully");
    }
    @PutMapping("/product/{id}")
    public ResponseEntity<String> updateProduct(@RequestBody ProductRequest productRequest,@PathVariable Integer id){
        productService.updateProduct(productRequest,id);
        return ResponseEntity.ok("Updated successfully");
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Integer id){
        productService.deleteProduct(id);
        return ResponseEntity.ok("Deleted Succesfully");
    }
}
