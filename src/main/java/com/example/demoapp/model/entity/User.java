package com.example.demoapp.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;


@Entity
@Table(name = "users")
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;

    @NotNull
    @Column(name = "name", unique = true, length = 32)
    private String name;

    @Column(name = "email",unique = true,length = 64)
    private String email;

    @NotNull
    @Column(name = "password", length = 128)
    private String password;

    @ToString.Exclude
    @OneToOne(mappedBy = "user")
    private Cart cart;
}
