package fr.epitech.cash.manager.entity;

import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.Instant;

@Entity
@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "street", nullable = false)
    private String street;

    @Column(name = "city", nullable = false)
    private String city;

    @Column(name = "zipCode", nullable = false)
    private String zipCode;

    @Column(name = "phoneNumber", nullable = false)
    private String phoneNumber;

    @ManyToOne
    @JoinColumn(name = "fk_id_role", nullable = false)
    private RoleEntity roleEntity;

    @Column(name = "creation_date")
    private Timestamp creation_date;

    @Column(name = "modification_date")
    private Timestamp modification_date;

}