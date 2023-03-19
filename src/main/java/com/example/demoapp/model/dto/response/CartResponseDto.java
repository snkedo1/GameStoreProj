package com.example.demoapp.model.dto.response;

import lombok.Data;

import java.util.List;

@Data
public class CartResponseDto {
    private Long cartId;
    private String owner;
    private List<ItemResponseDto> games;
}
