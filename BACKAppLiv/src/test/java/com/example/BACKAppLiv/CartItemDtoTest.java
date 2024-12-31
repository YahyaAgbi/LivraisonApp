package com.example.BACKAppLiv;

import com.example.BACKAppLiv.dto.CartItemDto;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CartItemDtoTest {

    @Test
    void testCartItemDto() {
        // Création d'une instance de CartItemDto
        CartItemDto cartItemDto = new CartItemDto(1L, "Product A", 100L, "Category A", "image1.jpg", 2);

        // Vérification des valeurs via les getters
        assertEquals(1L, cartItemDto.getProductId());
        assertEquals("Product A", cartItemDto.getProductName());
        assertEquals(100L, cartItemDto.getProductPrice());
        assertEquals("Category A", cartItemDto.getProductCategory());
        assertEquals("image1.jpg", cartItemDto.getProductImage());
        assertEquals(2, cartItemDto.getQuantity());
    }

    @Test
    void testSettersAndGetters() {
        // Création d'une instance vide
        CartItemDto cartItemDto = new CartItemDto(null, null, null, null, null, 0);

        // Utilisation des setters pour définir des valeurs
        cartItemDto.setProductId(2L);
        cartItemDto.setProductName("Product B");
        cartItemDto.setProductPrice(200L);
        cartItemDto.setProductCategory("Category B");
        cartItemDto.setProductImage("image2.jpg");
        cartItemDto.setQuantity(3);

        // Vérification des valeurs après avoir utilisé les setters
        assertEquals(2L, cartItemDto.getProductId());
        assertEquals("Product B", cartItemDto.getProductName());
        assertEquals(200L, cartItemDto.getProductPrice());
        assertEquals("Category B", cartItemDto.getProductCategory());
        assertEquals("image2.jpg", cartItemDto.getProductImage());
        assertEquals(3, cartItemDto.getQuantity());
    }

    @Test
    void testNoArgsConstructor() {
        // Test du constructeur sans arguments
        CartItemDto cartItemDto = new CartItemDto(null, null, null, null, null, 0);

        // Vérification des valeurs par défaut (null pour les String et Long, 0 pour int)
        assertNull(cartItemDto.getProductId());
        assertNull(cartItemDto.getProductName());
        assertNull(cartItemDto.getProductPrice());
        assertNull(cartItemDto.getProductCategory());
        assertNull(cartItemDto.getProductImage());
        assertEquals(0, cartItemDto.getQuantity());
    }
}
