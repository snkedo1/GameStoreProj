package com.example.demoapp.model.dto.response;

import lombok.Data;

import java.util.List;

@Data
public class GameResponseDto {
    private Long id;
    private String name;
    private int stock;
    private Double price;
    private String publisher;
    private String genre;
}
