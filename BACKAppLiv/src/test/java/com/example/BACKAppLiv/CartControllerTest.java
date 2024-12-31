package com.example.BACKAppLiv;

import com.example.BACKAppLiv.controller.CartController;
import com.example.BACKAppLiv.dto.CartItemDto;
import com.example.BACKAppLiv.service.CartService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CartControllerTest {

    @Mock
    private CartService cartService;

    @InjectMocks
    private CartController cartController;

    private CartItemDto cartItem;

    @BeforeEach
    void setUp() {
        cartItem = new CartItemDto(1L, "Product 1", 100L, "Category", "image.jpg", 2);
    }

    @Test
    void testGetCartItems() {
        // Given
        List<CartItemDto> cartItems = Arrays.asList(cartItem);
        when(cartService.getCartItems()).thenReturn(cartItems);

        // When
        List<CartItemDto> result = cartController.getCartItems();

        // Then
        assertEquals(1, result.size());
        assertEquals("Product 1", result.get(0).getProductName());
        verify(cartService, times(1)).getCartItems();
    }

    @Test
    void testAddProductToCart() {
        // Given
        Long productId = 1L;
        int quantity = 2;
        doNothing().when(cartService).addProductToCart(productId, quantity);

        // When
        ResponseEntity<String> response = cartController.addProductToCart(productId, quantity);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Product added to cart", response.getBody());
        verify(cartService, times(1)).addProductToCart(productId, quantity);
    }

    @Test
    void testRemoveFromCart() {
        // Given
        Long productId = 1L;
        doNothing().when(cartService).removeProductFromCart(productId);

        // When
        ResponseEntity<String> response = cartController.removeFromCart(productId);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Produit supprimé du panier avec succès", response.getBody());
        verify(cartService, times(1)).removeProductFromCart(productId);
    }

    @Test
    void testRemoveFromCart_ProductNotFound() {
        // Given
        Long productId = 999L;
        doThrow(new IllegalArgumentException("Product not found")).when(cartService).removeProductFromCart(productId);

        // When
        ResponseEntity<String> response = cartController.removeFromCart(productId);

        // Then
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(response.getBody().contains("Aucun élément trouvé dans le panier"));
    }

    @Test
    void testClearCart() {
        // Given
        doNothing().when(cartService).clearCart();

        // When
        ResponseEntity<String> response = cartController.clearCart();

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Panier vidé", response.getBody());
        verify(cartService, times(1)).clearCart();
    }
}
