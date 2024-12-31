package com.example.BACKAppLiv.controller;

import com.example.BACKAppLiv.dto.CartItemDto;
import com.example.BACKAppLiv.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    // Récupère tous les éléments du panier
    @GetMapping
    public List<CartItemDto> getCartItems() {
        return cartService.getCartItems();
    }

    // Ajoute un produit au panier
    @PostMapping("/add")
    public ResponseEntity<String> addProductToCart(@RequestParam Long productId, @RequestParam int quantity) {
        cartService.addProductToCart(productId, quantity);
        return ResponseEntity.ok("Product added to cart");
    }

    // Supprime un produit du panier en fonction de l'ID du produit
    @DeleteMapping("/remove/{productId}")
    public ResponseEntity<String> removeFromCart(@PathVariable Long productId) {
        try {
            cartService.removeProductFromCart(productId);
            return ResponseEntity.ok("Produit supprimé du panier avec succès");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Aucun élément trouvé dans le panier avec l'ID produit " + productId);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erreur lors de la suppression du produit du panier.");
        }
    }



    // Vide tout le panier
    @DeleteMapping("/clear")
    public ResponseEntity<String> clearCart() {
        cartService.clearCart();
        return ResponseEntity.ok("Panier vidé");
    }
}
