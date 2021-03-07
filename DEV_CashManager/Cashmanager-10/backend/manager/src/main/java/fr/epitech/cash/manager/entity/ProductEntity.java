package fr.epitech.cash.manager.entity;

import lombok.*;
import java.sql.Timestamp;
import java.time.Instant;

import javax.persistence.*;

@Entity
@Table(name = "Products")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(unique = true, name = "ean_code", nullable = false)
    private Integer ean_code;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "price", nullable = false)
    private Double price;

    @Column(name = "stock", nullable = false)
    private Integer stock;

    @Column(name = "creation_date")
    private Timestamp creation_date;

    @Column(name = "modification_date")
    private Timestamp modification_date;

}