package com.example.BACKAppLiv;

import com.example.BACKAppLiv.dto.ProductDto;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProductDtoTest {

    @Test
    void testProductDtoConstructorAndGetters() {
        // Création d'un objet ProductDto en utilisant le constructeur
        ProductDto productDto = new ProductDto("Product 1", 100L, "Category 1", "image1.png");

        // Vérification que l'objet n'est pas null
        assertNotNull(productDto);

        // Vérification que les valeurs des propriétés sont correctes
        assertEquals("Product 1", productDto.getName());
        assertEquals(100L, productDto.getPrice());
        assertEquals("Category 1", productDto.getCategory());
        assertEquals("image1.png", productDto.getImage());
    }

    @Test
    void testProductDtoSettersAndGetters() {
        // Création d'un objet ProductDto avec le constructeur par défaut
        ProductDto productDto = new ProductDto();

        // Utilisation des setters pour définir les valeurs
        productDto.setName("Product 2");
        productDto.setPrice(200L);
        productDto.setCategory("Category 2");
        productDto.setImage("image2.png");

        // Vérification que les getters renvoient les valeurs correctes
        assertEquals("Product 2", productDto.getName());
        assertEquals(200L, productDto.getPrice());
        assertEquals("Category 2", productDto.getCategory());
        assertEquals("image2.png", productDto.getImage());
    }

    @Test
    void testProductDtoDefaultConstructor() {
        // Création d'un objet ProductDto avec le constructeur par défaut
        ProductDto productDto = new ProductDto();

        // Vérification que les propriétés sont nulles ou initialisées avec des valeurs par défaut
        assertNull(productDto.getName());
        assertNull(productDto.getCategory());
        assertNull(productDto.getImage());
        assertNull(productDto.getPrice());
    }
}
