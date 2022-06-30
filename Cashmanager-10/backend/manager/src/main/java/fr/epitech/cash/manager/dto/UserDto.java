package fr.epitech.cash.manager.dto;

import lombok.*;

import java.sql.Timestamp;
import java.time.Instant;

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
public class UserDto {

    private Integer id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String street;
    private String city;
    private String zipCode;
    private String phoneNumber;
    private String role;
    private Timestamp creation_date;
    private Timestamp modification_date;
}