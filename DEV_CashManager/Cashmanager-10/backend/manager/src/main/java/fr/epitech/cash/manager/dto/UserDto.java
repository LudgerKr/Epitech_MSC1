package fr.epitech.cash.manager.dto;

import lombok.*;

import java.sql.Timestamp;
import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
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