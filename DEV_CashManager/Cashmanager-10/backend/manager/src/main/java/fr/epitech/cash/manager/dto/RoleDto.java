package fr.epitech.cash.manager.dto;

import java.sql.Timestamp;
import java.time.Instant;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RoleDto {

    private Integer id;
    private String name;
    private Timestamp creation_date;
    private Timestamp modification_date;

}