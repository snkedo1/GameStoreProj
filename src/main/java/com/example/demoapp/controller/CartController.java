package com.example.demoapp.controller;

import com.example.demoapp.model.dto.request.GameRequestDto;
import com.example.demoapp.model.dto.response.CartResponseDto;
import com.example.demoapp.service.CartServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartServiceImpl cartService;

    @GetMapping
    public List<CartResponseDto> getAllCarts() {
        return cartService.getAllCarts();
    }

    @PostMapping("/add/{cartId}")
    public CartResponseDto addItemsToCart(@RequestBody List<GameRequestDto> items, @PathVariable Long cartId) {
        return cartService.addItemsToCart(items, cartId);
    }
}