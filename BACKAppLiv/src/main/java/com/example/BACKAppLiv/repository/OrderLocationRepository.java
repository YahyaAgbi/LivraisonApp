package com.example.BACKAppLiv.repository;

import com.example.BACKAppLiv.model.OrderLocation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrderLocationRepository extends JpaRepository<OrderLocation, Long> {
    Optional<OrderLocation> findByOrderId(Long orderId);
}
