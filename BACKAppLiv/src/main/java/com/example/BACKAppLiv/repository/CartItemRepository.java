package com.example.BACKAppLiv.repository;

import com.example.BACKAppLiv.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    void deleteById(Long id);
}
