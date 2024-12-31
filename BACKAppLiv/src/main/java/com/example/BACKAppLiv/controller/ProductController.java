package com.example.BACKAppLiv.controller;

import com.example.BACKAppLiv.dto.ProductDto;
import com.example.BACKAppLiv.model.Product;
import com.example.BACKAppLiv.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class ProductController {

    private final JwtDecoder jwtDecoder;
    private final ProductService productService;

    @Autowired
    public ProductController(JwtDecoder jwtDecoder, ProductService productService) {
        this.jwtDecoder = jwtDecoder;
        this.productService = productService;
    }

    // Ajouter un produit
    @PostMapping("/add-product")
    public ResponseEntity<String> addProduct(@RequestHeader("Authorization") String authorizationHeader,
                                             @RequestBody ProductDto productDto) {
        if (!isValidJwt(authorizationHeader)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("JWT invalide ou expiré");
        }

        productService.addProduct(productDto);
        return ResponseEntity.ok("Produit ajouté avec succès");
    }

    // Supprimer un produit
    @DeleteMapping("/delete-product/{id}")
    public ResponseEntity<String> deleteProduct(@RequestHeader("Authorization") String authorizationHeader,
                                                @PathVariable Long id) {
        if (!isValidJwt(authorizationHeader)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("JWT invalide ou expiré");
        }

        try {
            productService.deleteProduct(id);
            return ResponseEntity.ok("Produit supprimé avec succès");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // Modifier un produit
    @PutMapping("/update-product/{id}")
    public ResponseEntity<String> updateProduct(@RequestHeader("Authorization") String authorizationHeader,
                                                @PathVariable Long id,
                                                @RequestBody ProductDto productDto) {
        if (!isValidJwt(authorizationHeader)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("JWT invalide ou expiré");
        }

        try {
            productService.updateProduct(id, productDto);
            return ResponseEntity.ok("Produit mis à jour avec succès");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // Afficher tous les produits
    @GetMapping("/products")
    public ResponseEntity<List<Product>> getAllProducts(@RequestHeader("Authorization") String authorizationHeader) {
        if (!isValidJwt(authorizationHeader)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        }

        List<Product> products = productService.getAllProducts();
        return ResponseEntity.ok(products);
    }

    // Afficher un produit par ID
    @GetMapping("/product/{id}")
    public ResponseEntity<Object> getProductById(@RequestHeader("Authorization") String authorizationHeader,
                                                 @PathVariable Long id) {
        if (!isValidJwt(authorizationHeader)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("JWT invalide ou expiré");
        }

        Optional<Product> product = productService.getProductById(id);
        if (product.isPresent()) {
            return ResponseEntity.ok(product.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Produit non trouvé avec l'ID : " + id);
        }
    }

    // Méthode pour valider le JWT
    private boolean isValidJwt(String authorizationHeader) {
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            return false;
        }

        String jwtToken = authorizationHeader.substring(7);
        try {
            Jwt decodedJwt = jwtDecoder.decode(jwtToken);
            return decodedJwt != null && !decodedJwt.getExpiresAt().isBefore(Instant.now());
        } catch (Exception e) {
            return false;
        }
    }
}
