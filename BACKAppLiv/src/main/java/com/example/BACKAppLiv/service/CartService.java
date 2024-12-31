package com.example.BACKAppLiv.service;

import com.example.BACKAppLiv.dto.CartItemDto;
import com.example.BACKAppLiv.model.CartItem;
import com.example.BACKAppLiv.model.Product;
import com.example.BACKAppLiv.repository.CartItemRepository;
import com.example.BACKAppLiv.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CartService {

    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;

    public CartService(CartItemRepository cartItemRepository, ProductRepository productRepository) {
        this.cartItemRepository = cartItemRepository;
        this.productRepository = productRepository;
    }

    // Récupère tous les éléments du panier
    public List<CartItemDto> getCartItems() {
        return cartItemRepository.findAll().stream().map(cartItem -> {
            Product product = cartItem.getProduct();
            return new CartItemDto(
                    product.getId(),
                    product.getName(),
                    product.getPrice(),
                    product.getCategory(),
                    product.getImage(),
                    cartItem.getQuantity()
            );
        }).collect(Collectors.toList());
    }

    // Ajoute un produit au panier
    public void addProductToCart(Long productId, int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than zero");
        }

        // Vérifie si le produit existe
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found with ID: " + productId));

        // Vérifie si un CartItem existe déjà pour ce produit
        Optional<CartItem> existingCartItem = cartItemRepository.findAll().stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst();

        // Si l'élément existe déjà, on met à jour la quantité
        if (existingCartItem.isPresent()) {
            CartItem cartItem = existingCartItem.get();
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
            cartItemRepository.save(cartItem);
        } else {
            // Sinon, on crée un nouvel élément de panier
            CartItem newItem = new CartItem();
            newItem.setProduct(product);
            newItem.setQuantity(quantity);
            cartItemRepository.save(newItem);
        }
    }

    // Supprime un produit du panier
    public void removeProductFromCart(Long productId) {
        // Recherche du CartItem associé à productId
        Optional<CartItem> cartItemOpt = cartItemRepository.findAll().stream()
                .filter(cartItem -> cartItem.getProduct().getId().equals(productId))
                .findFirst();

        if (cartItemOpt.isPresent()) {
            CartItem cartItem = cartItemOpt.get();
            cartItemRepository.delete(cartItem);  // Supprime l'élément du panier
        } else {
            throw new IllegalArgumentException("Cart item not found with product ID: " + productId);
        }
    }



    // Vide tout le panier
    public void clearCart() {
        cartItemRepository.deleteAll();
    }
}
