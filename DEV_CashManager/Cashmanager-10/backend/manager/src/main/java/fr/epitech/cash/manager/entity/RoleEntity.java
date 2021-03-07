package fr.epitech.cash.manager.entity;

import lombok.*;
import java.sql.Timestamp;
import java.time.Instant;

import javax.persistence.*;

@Entity
@Table(name = "Roles")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
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