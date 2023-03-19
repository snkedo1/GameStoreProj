package com.example.demoapp.repo;

import com.example.demoapp.model.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepo extends JpaRepository <Cart,Long > {
    List<Cart> findAllByUserId(Long userId);
}
