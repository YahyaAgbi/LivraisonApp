package com.example.BACKAppLiv;

import com.example.BACKAppLiv.dto.UserLoginDto;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserLoginDtoTest {

    @Test
    void testUserLoginDtoWithConstructor() {
        // Création d'une instance de UserLoginDto avec le constructeur avec arguments
        UserLoginDto userLoginDto = new UserLoginDto("username123", "email@example.com", "password123");

        // Vérification des valeurs via les getters
        assertEquals("username123", userLoginDto.getUsername());
        assertEquals("email@example.com", userLoginDto.getEmail());
        assertEquals("password123", userLoginDto.getPassword());
    }

    @Test
    void testUserLoginDtoWithSettersAndGetters() {
        // Création d'une instance de UserLoginDto avec le constructeur par défaut
        UserLoginDto userLoginDto = new UserLoginDto();

        // Utilisation des setters pour définir les valeurs
        userLoginDto.setUsername("username456");
        userLoginDto.setEmail("email456@example.com");
        userLoginDto.setPassword("password456");

        // Vérification des valeurs après avoir utilisé les setters
        assertEquals("username456", userLoginDto.getUsername());
        assertEquals("email456@example.com", userLoginDto.getEmail());
        assertEquals("password456", userLoginDto.getPassword());
    }

    @Test
    void testNoArgsConstructor() {
        // Test du constructeur par défaut (sans arguments)
        UserLoginDto userLoginDto = new UserLoginDto();

        // Vérification des valeurs par défaut (null pour les String)
        assertNull(userLoginDto.getUsername());
        assertNull(userLoginDto.getEmail());
        assertNull(userLoginDto.getPassword());
    }
}
