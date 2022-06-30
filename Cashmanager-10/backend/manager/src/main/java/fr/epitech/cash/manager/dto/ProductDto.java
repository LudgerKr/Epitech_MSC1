package fr.epitech.cash.manager.dto;

import java.sql.Timestamp;
import java.time.Instant;

import lombok.*;

// Create automatically all getters
@Getter
// Create automatically all setter
@Setter
// Create automatically all constructor with arguments
@AllArgsConstructor
// Create automatically all getters without arguments
@NoArgsConstructor
// Convert a Entity into DTO with the build function
@Builder
public class ProductDto {

    private Integer id;
    private Integer ean_code;
    private String name;
    private String description;
    private Double price;
    private Integer stock;
    private Timestamp creation_date;
    private Timestamp modification_date;

}