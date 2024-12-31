package com.example.BACKAppLiv.repository;

import com.example.BACKAppLiv.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    // Aucune méthode spécifique ici pour ce test
}

