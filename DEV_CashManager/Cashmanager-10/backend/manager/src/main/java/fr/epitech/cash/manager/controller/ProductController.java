package fr.epitech.cash.manager.controller;

import fr.epitech.cash.manager.dto.ProductDto;
import fr.epitech.cash.manager.entity.ProductEntity;
import fr.epitech.cash.manager.repository.ProductRepository;
import fr.epitech.cash.manager.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductRepository productRepository;

    @PostMapping(value = "/api/auth/products")
    public ResponseEntity<ProductDto> createProduct(
            @RequestParam Integer eanCode, @RequestParam String name, @RequestParam String description,
            @RequestParam Double price, @RequestParam Integer stock) {

        ProductDto productDto = productService.createProduct(
                eanCode, name, description, price,
                stock
        );
        return new ResponseEntity<>(productDto, HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/api/auth/products/{id}")
    public ResponseEntity<HttpStatus> deleteProduct(@PathVariable Integer id) {

        productService.deleteProduct(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping(value = "/api/auth/products")
    public ResponseEntity<List<ProductDto>> getProducts() {
        List<ProductDto> productDtoList = productService.getProducts();

        return new ResponseEntity<>(productDtoList, HttpStatus.OK);
    }

    @GetMapping(value = "/api/auth/products/{id}")
    public ResponseEntity<ProductDto> getProduct(@PathVariable Integer id) {
        ProductDto productDto = productService.getProduct(id);
        return new ResponseEntity<>(productDto, HttpStatus.OK);
    }

    @PatchMapping("/api/auth/products/{id}")
    public ResponseEntity<HttpStatus> updatePerson(@PathVariable("id") Integer id,
                                             @RequestBody ProductDto ProductDto) {
        productService.updateProduct(id, ProductDto);

        return new ResponseEntity<>(HttpStatus.OK);
    };
}