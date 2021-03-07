package fr.epitech.cash.manager.service;

import fr.epitech.cash.manager.dto.ProductDto;

import java.util.List;

public interface ProductService {

    /**
     * Create a new product
     * @param ean_code : ean_code
     * @param name : name
     * @param description : description
     * @param price : price
     * @param stock : stock
     * @return : ProductDto
     */
    ProductDto createProduct(Integer ean_code, String name, String description,
                       Double price, Integer stock);

    /**
     * Delete an product by its id
     * @param id : id
     */
    void deleteProduct(Integer id);

    /**
     * Return a list of all products
     * @return : List<ProductDto>
     */
    List<ProductDto> getProducts();

    /**
     * Return on product by id
     * @return ProductDto
     * @param id : id
     */
    ProductDto getProduct(Integer id);

    /**
     * Update an product
     * @param id : id
     * @param productDto : productDto
     */
    void updateProduct(Integer id, ProductDto productDto);

}