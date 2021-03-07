package fr.epitech.cash.manager.dto;

import java.sql.Timestamp;
import java.time.Instant;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
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