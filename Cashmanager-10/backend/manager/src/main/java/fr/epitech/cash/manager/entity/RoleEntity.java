package fr.epitech.cash.manager.entity;

import lombok.*;
import java.sql.Timestamp;
import java.time.Instant;

import javax.persistence.*;

// Indicate its an Entity and a table in the Database
@Entity
@Table(name = "Roles")
// Create automatically all getters
@Getter
// Create automatically all setter
@Setter
// Create automatically all constructor with arguments
@AllArgsConstructor
// Create automatically all getters without arguments
@NoArgsConstructor
// Set easily all values of an Entity / DTO
@Builder
public class RoleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(unique = true, name = "name", nullable = false)
    private String name;

    @Column(name = "creation_date")
    private Timestamp creation_date;

    @Column(name = "modification_date")
    private Timestamp modification_date;

}