package com.example.demoapp.service;

import com.example.demoapp.model.dto.request.GameRequestDto;
import com.example.demoapp.model.dto.response.CartResponseDto;
import com.example.demoapp.model.dto.response.ItemResponseDto;
import com.example.demoapp.model.entity.Cart;
import com.example.demoapp.model.entity.CartItem;
import com.example.demoapp.model.entity.Game;
import com.example.demoapp.model.entity.User;
import com.example.demoapp.repo.CartRepo;
import com.example.demoapp.repo.GameRepo;
import com.example.demoapp.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartServiceImpl {

    private final CartRepo cartRepo;
    private final GameRepo gameRepo;
    private final UserRepository userRepository;

    public List<CartResponseDto> getAllCarts() {
        return cartRepo.findAll().stream()
                .map(this::mapToResponseDto)
                .collect(Collectors.toList());
    }

    public CartResponseDto addItemsToCart(List<GameRequestDto> items, Long cartId) {
        Cart cart = cartRepo.findById(cartId)
                .orElse(new Cart());
        UserDetailsImpl principal = (UserDetailsImpl)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User dorin = userRepository.findByUsernameIsIgnoreCase(principal.getUsername());
        cart.setUser(dorin);
        items.forEach(item -> cart.getItems().add(createCartItem(cart, item)));
        Cart updatedCart = cartRepo.save(cart);
        return mapToResponseDto(updatedCart);
    }

    private CartItem createCartItem(Cart cart, GameRequestDto item) {
        CartItem itemEntity = new CartItem();
        itemEntity.setCart(cart);
        Game game = gameRepo.findByNameIs(item.getName())
                .orElseThrow(() -> new RuntimeException("Could not find game: " + item.getName()));
        itemEntity.setGame(game);
        return itemEntity;
    }

    public CartResponseDto removeCartItem(Long cartId,Long cartItemId){
        Cart cart=cartRepo.findById(cartId).orElseThrow(()->new RuntimeException("Cant find cart with this id"));
        CartItem item=cart.getItems().stream().filter(cartItem -> cartItem.getId().equals(cartItemId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Could not find item with ID: " + cartItemId));
        cart.getItems().remove(item);
        cartRepo.save(cart);
        return mapToResponseDto(cart);
    }

    private CartResponseDto mapToResponseDto(Cart cartEntity) {
        CartResponseDto response = new CartResponseDto();
        response.setCartId(cartEntity.getId());
        response.setOwner(cartEntity.getUser().getUsername());
        response.setGames(cartEntity.getItems().stream()
                .map(item -> {
                    var itemResponseDto = new ItemResponseDto();
                    itemResponseDto.setGameName(item.getGame().getName());
                    return itemResponseDto;
                }).collect(Collectors.toList()));
        return response;
    }
}
