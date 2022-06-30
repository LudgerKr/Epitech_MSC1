package fr.epitech.cash.manager.service.impl;

import fr.epitech.cash.manager.dto.ProductDto;
import fr.epitech.cash.manager.dto.mapper.ProductMapper;
import fr.epitech.cash.manager.entity.ProductEntity;
import fr.epitech.cash.manager.repository.ProductRepository;
import fr.epitech.cash.manager.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Optional;

@Service("productDetailsService")
public class ProductServiceImpl implements ProductService {

    // Prepare the ProductRepository
    @Autowired
    private ProductRepository productRepository;

    // Prepare the ProductMapper
    @Autowired
    private ProductMapper productMapper;

    @Override
    // Transactional indicates it will modify the database
    @Transactional
    // Create a product
    public ProductDto createProduct(Integer ean_code, String name, String description,
                              Double price, Integer stock)
    {
        ProductEntity productEntity = ProductEntity.builder()
                                        .ean_code(ean_code)
                                        .name(name)
                                        .description(description)
                                        .price(price)
                                        .stock(stock)
                                        // Set datetime
                                        .creation_date(new Timestamp(System.currentTimeMillis()))
                                        .modification_date(new Timestamp(System.currentTimeMillis()))
                                        .build();

        // Record the product in the database
        productEntity = productRepository.save(productEntity);

        return ProductDto.builder()
                .id(productEntity.getId())
                .ean_code(productEntity.getEan_code())
                .name(productEntity.getName())
                .description(productEntity.getDescription())
                .price(productEntity.getPrice())
                .stock(productEntity.getStock())
                .creation_date(productEntity.getCreation_date())
                .modification_date(productEntity.getModification_date())
                .build();
    }

    @Override
    // Transactional indicates it will modify the database
    @Transactional
    // Delete a product
    public void deleteProduct(Integer id) {
        productRepository.deleteById(id);
    }

    @Override
    // Return all products
    public List<ProductDto> getProducts()
    {
        List<ProductEntity> productEntityList = productRepository.findAll();
        return productMapper.convertListProductEntity(productEntityList);
    }

    @Override
    // Return a product with its id
    public ProductDto getProduct(Integer id)
    {
        ProductEntity productEntity = productRepository.findById(id).orElse(null);
        if(productEntity == null)
            return null;
        return productMapper.convert(productEntity);
    }

    @Override
    // Transactional indicates it will modify the database
    @Transactional
    // Update a product with ids and DTO (json)
    public void updateProduct(Integer id, ProductDto productDto)
    {
        Optional<ProductEntity> productEntityOptional = productRepository.findById(id);
        if (!productEntityOptional.isPresent()) {
            ResponseEntity.notFound().build();
            return;
        }

        ProductEntity productEntity = productEntityOptional.get();

        // Check if all values are in the DTO for update or not
        if (productDto.getEan_code() != null) {
            productEntity.setEan_code(productDto.getEan_code());
        }
        if (productDto.getName() != null) {
            productEntity.setName(productDto.getName());
        }
        if (productDto.getDescription() != null) {
            productEntity.setDescription(productDto.getDescription());
        }
        if (productDto.getPrice() != null) {
            productEntity.setPrice(productDto.getPrice());
        }
        if (productDto.getStock() != null) {
            productEntity.setStock(productDto.getStock());
        }

        // Set Datetime to the modification date
        productEntity.setModification_date(new Timestamp(System.currentTimeMillis()));

        // Record the modify product in the database
        productRepository.save(productEntity);
    }
}