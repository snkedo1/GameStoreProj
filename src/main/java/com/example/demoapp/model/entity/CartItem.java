package com.example.demoapp.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

@Data
@Entity
@Table(name = "cart_item")
public class CartItem {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @ToString.Exclude
        @ManyToOne
        @JoinColumn(name = "cart_id", referencedColumnName = "id")
        private Cart cart;

        @ManyToOne
        @JoinColumn(name = "game_id", referencedColumnName = "id")
        private Game game;

}