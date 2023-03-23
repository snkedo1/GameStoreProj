package com.example.demoapp.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;


@Entity
@Table(name = "game")
@Data
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(unique = true, length = 80)
    private String name;

    @NotNull
    @Column
    @Min(0)
    private int stock;

    @NotNull
    @Column(precision = 2)
    private Double price;

    @ManyToOne
    @JoinColumn(name = "publisher_id", referencedColumnName = "id")
    private Publisher publisher;

    @ManyToOne
    @JoinColumn(name = "genre_id", referencedColumnName = "id")
    private Genre genre;

    public int decreaseStock(int quantity) {
       return stock -= quantity;
    }
}
