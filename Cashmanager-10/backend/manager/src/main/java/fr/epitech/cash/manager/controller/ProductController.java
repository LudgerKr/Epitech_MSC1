package fr.epitech.cash.manager.controller;

import fr.epitech.cash.manager.dto.ProductDto;
import fr.epitech.cash.manager.repository.ProductRepository;
import fr.epitech.cash.manager.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
public class ProductController {

    // Prepare the ProductService class
    @Autowired
    private ProductService productService;

    // PRepare the ProductRepository
    @Autowired
    private ProductRepository productRepository;

    // Create a product
    @PostMapping(value = "/api/auth/products")
    public ResponseEntity<ProductDto> createProduct(
            @RequestParam Integer eanCode, @RequestParam String name, @RequestParam String description,
            @RequestParam Double price, @RequestParam Integer stock) {

        // Convert the ProductEntity into DTO Json
        ProductDto productDto = productService.createProduct(
                eanCode, name, description, price,
                stock
        );
        return new ResponseEntity<>(productDto, HttpStatus.CREATED);
    }

    // Delete the product with its id
    @DeleteMapping(value = "/api/auth/products/{id}")
    public ResponseEntity<HttpStatus> deleteProduct(@PathVariable Integer id) {

        productService.deleteProduct(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Get all products
    @GetMapping(value = "/api/auth/products")
    public ResponseEntity<List<ProductDto>> getProducts() {
        List<ProductDto> productDtoList = productService.getProducts();

        return new ResponseEntity<>(productDtoList, HttpStatus.OK);
    }

    // Get a products with its id
    @GetMapping(value = "/api/auth/products/{id}")
    public ResponseEntity<ProductDto> getProduct(@PathVariable Integer id) {
        ProductDto productDto = productService.getProduct(id);
        return new ResponseEntity<>(productDto, HttpStatus.OK);
    }

    // Get a products with its id
    @PatchMapping("/api/auth/products/{id}")
    public ResponseEntity<HttpStatus> updatePerson(@PathVariable("id") Integer id,
                                             @RequestBody ProductDto ProductDto) {
        productService.updateProduct(id, ProductDto);

        return new ResponseEntity<>(HttpStatus.OK);
    };
}