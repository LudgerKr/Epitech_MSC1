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
public class RoleDto {

    private Integer id;
    private String name;
    private Timestamp creation_date;
    private Timestamp modification_date;

}