package dev.buddly.flow_sync.service.impl;


import dev.buddly.flow_sync.dto.ProductRequest;
import dev.buddly.flow_sync.dto.ProductResponse;
import dev.buddly.flow_sync.dto.converter.ProductResponseConverter;
import dev.buddly.flow_sync.exception.AlreadyExistsException;
import dev.buddly.flow_sync.exception.NotFoundException;
import dev.buddly.flow_sync.model.Product;
import dev.buddly.flow_sync.model.Store;
import dev.buddly.flow_sync.repository.StoreRepository;
import dev.buddly.flow_sync.repository.ProductRepository;
import dev.buddly.flow_sync.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;
    private final StoreRepository storeRepository;
    private final ProductResponseConverter productResponseConverter;

    public ProductServiceImpl(ProductRepository productRepository, ModelMapper modelMapper, StoreRepository storeRepository, ProductResponseConverter productResponseConverter) {
        this.productRepository = productRepository;
        this.modelMapper = modelMapper;
        this.storeRepository = storeRepository;
        this.productResponseConverter = productResponseConverter;
    }

    @Override
    public List<ProductResponse> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return products.stream()
                .map(productResponseConverter::convert)
                .collect(Collectors.toList());
    }

    @Override
    public ProductResponse findById(Integer id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Product not found"));
        return productResponseConverter.convert(product);
    }

    @Override
    public List<ProductResponse> findByProductId(String productId) {
        List<Product> products = productRepository.findAllByProductIdContaining(productId);
        return products.stream()
                .filter(item->item.getProductId().contains(productId))
                .map(productResponseConverter::convert)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductResponse> findByStoreId(String storeId) {
        List<Product> products = productRepository.findAllByStore_StoreIdContaining(storeId);
        return products.stream()
                .map(productResponseConverter::convert)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductResponse> findByProductName(String productName) {
        List<Product> products = productRepository.findAllByProductNameContaining(productName);
        return products.stream()
                .filter(item->item.getProductName().contains(productName))
                .map(productResponseConverter::convert)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductResponse> findByStat(String stat) {
        List<Product> products = productRepository.findAllByStatContaining(stat);
        return products.stream()
                .filter(item->item.getStat().contains(stat))
                .map(productResponseConverter::convert)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductResponse> findByBatteryLevel(String batteryLevel) {
        List<Product> products = productRepository.findAllByBatteryLevelContaining(batteryLevel);
        return products.stream()
                .filter(item->item.getBatteryLevel().contains(batteryLevel))
                .map(productResponseConverter::convert)
                .collect(Collectors.toList());
    }

    @Override
    public ProductRequest addProduct(ProductRequest productDto) {
        var existingProductByProductId = productRepository.findByProductId(productDto.getProductId());
        var existingProductByProductName = productRepository.findByProductName(productDto.getProductName());
        if (existingProductByProductId != null){
            log.error("Product id already have");
            throw new AlreadyExistsException("Product id already have");
        }
        if (existingProductByProductName != null){
            log.error("Product name already have");
            throw new AlreadyExistsException("Product name already have");
        }
        Store store = storeRepository.findById(productDto.getStore_id())
                .orElseThrow(()->new NotFoundException("Store not found"));
        var product = modelMapper.map(productDto,Product.class);
        product.setStore(store);
        Product savedProduct = productRepository.save(product);
        return modelMapper.map(savedProduct,ProductRequest.class);
    }

    @Override
    public ProductRequest updateProduct(ProductRequest productDto, Integer id) {
        Product product = productRepository.findById(id)
                .orElseThrow(()->new NotFoundException("Product not found"));
        Store store = storeRepository.findById(productDto.getStore_id())
                .orElseThrow(()->new NotFoundException("Store not found"));
        product.setProductId(productDto.getProductId());
        product.setProductName(productDto.getProductName());
        product.setStat(productDto.getStat());
        product.setBatteryLevel(productDto.getBatteryLevel());
        product.setPassingTime(productDto.getPassingTime());
        product.setReadingTime(LocalDateTime.parse(productDto.getReadingTime()));
        product.setStore(store);
        Product savedProduct = productRepository.save(product);
        return modelMapper.map(savedProduct,ProductRequest.class);
    }

    @Override
    public void deleteProduct(Integer id) {
        Product product = productRepository.findById(id)
                .orElseThrow(()->new NotFoundException("Product not found"));
        productRepository.delete(product);
    }
}
