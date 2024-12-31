package com.example.BACKAppLiv;

import com.example.BACKAppLiv.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.reactive.function.client.WebClient;

import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private WebClient.Builder webClientBuilder;

    @Mock
    private WebClient webClient;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(webClientBuilder.build()).thenReturn(webClient);
    }

    @Test
    void testRegisterUser_Success() {
        // Arrange
        // Add test logic here

        // Act
        // Call the method under test

        // Assert
        // Add assertions
    }

    @Test
    void testLoginUser_Success() {
        // Arrange
        // Add test logic here

        // Act
        // Call the method under test

        // Assert
        // Add assertions
    }
}
