package com.example.BACKAppLiv;

import com.example.BACKAppLiv.dto.ProductDto;
import com.example.BACKAppLiv.model.Product;
import com.example.BACKAppLiv.repository.ProductRepository;
import com.example.BACKAppLiv.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void addProduct_ShouldSaveProduct() {
        // Arrange
        ProductDto productDto = new ProductDto("Product1", (long) 100.0, "Category1", "image.jpg");
        Product product = new Product();
        when(productRepository.save(any(Product.class))).thenReturn(product);

        // Act
        productService.addProduct(productDto);

        // Assert
        ArgumentCaptor<Product> productCaptor = ArgumentCaptor.forClass(Product.class);
        verify(productRepository, times(1)).save(productCaptor.capture());
        Product savedProduct = productCaptor.getValue();
        assertEquals(productDto.getName(), savedProduct.getName());
        assertEquals(productDto.getPrice(), savedProduct.getPrice());
        assertEquals(productDto.getCategory(), savedProduct.getCategory());
        assertEquals(productDto.getImage(), savedProduct.getImage());
    }

    @Test
    void deleteProduct_ShouldDeleteIfExists() {
        // Arrange
        Long productId = 1L;
        when(productRepository.existsById(productId)).thenReturn(true);

        // Act
        productService.deleteProduct(productId);

        // Assert
        verify(productRepository, times(1)).deleteById(productId);
    }

    @Test
    void deleteProduct_ShouldThrowExceptionIfNotExists() {
        // Arrange
        Long productId = 1L;
        when(productRepository.existsById(productId)).thenReturn(false);

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> productService.deleteProduct(productId));
        assertEquals("Produit avec l'ID " + productId + " n'existe pas.", exception.getMessage());
        verify(productRepository, never()).deleteById(productId);
    }

    @Test
    void updateProduct_ShouldUpdateIfExists() {
        // Arrange
        Long productId = 1L;
        Product existingProduct = new Product();
        ProductDto productDto = new ProductDto("UpdatedProduct", (long) 200.0, "UpdatedCategory", "updatedImage.jpg");

        when(productRepository.findById(productId)).thenReturn(Optional.of(existingProduct));
        when(productRepository.save(any(Product.class))).thenReturn(existingProduct);

        // Act
        productService.updateProduct(productId, productDto);

        // Assert
        verify(productRepository, times(1)).save(existingProduct);
        assertEquals(productDto.getName(), existingProduct.getName());
        assertEquals(productDto.getPrice(), existingProduct.getPrice());
        assertEquals(productDto.getCategory(), existingProduct.getCategory());
        assertEquals(productDto.getImage(), existingProduct.getImage());
    }

    @Test
    void updateProduct_ShouldThrowExceptionIfNotExists() {
        // Arrange
        Long productId = 1L;
        ProductDto productDto = new ProductDto("UpdatedProduct", (long) 200.0, "UpdatedCategory", "updatedImage.jpg");

        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> productService.updateProduct(productId, productDto));
        assertEquals("Produit avec l'ID " + productId + " n'existe pas.", exception.getMessage());
        verify(productRepository, never()).save(any(Product.class));
    }

    @Test
    void getAllProducts_ShouldReturnListOfProducts() {
        // Arrange
        List<Product> products = List.of(new Product(), new Product());
        when(productRepository.findAll()).thenReturn(products);

        // Act
        List<Product> result = productService.getAllProducts();

        // Assert
        assertEquals(products.size(), result.size());
        verify(productRepository, times(1)).findAll();
    }

    @Test
    void getProductById_ShouldReturnProductIfExists() {
        // Arrange
        Long productId = 1L;
        Product product = new Product();
        when(productRepository.findById(productId)).thenReturn(Optional.of(product));

        // Act
        Optional<Product> result = productService.getProductById(productId);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(product, result.get());
        verify(productRepository, times(1)).findById(productId);
    }

    @Test
    void getProductById_ShouldReturnEmptyIfNotExists() {
        // Arrange
        Long productId = 1L;
        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        // Act
        Optional<Product> result = productService.getProductById(productId);

        // Assert
        assertTrue(result.isEmpty());
        verify(productRepository, times(1)).findById(productId);
    }
}
