package com.modelsis.backendAhamada.Service;

import com.modelsis.backendAhamada.Exception.ProductNotFoundException;
import com.modelsis.backendAhamada.Repository.ProductRepository;
import com.modelsis.backendAhamada.models.Product;
import com.modelsis.backendAhamada.service.ProductService;
import com.modelsis.backendAhamada.service.impl.ProductServiceImp;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImp productService;


    // Méthode exécutée avant chaque test
    @BeforeEach
    void setUp() {
        // Ajoutez ici le code d'initialisation, si nécessaire

    }
    @Test
    void testCreateProduct() {
        // Given
        Product mockProduct = new Product();
        mockProduct.setName("Test Product");
        mockProduct.setCreatedAt(LocalDateTime.now());
        when(productRepository.save(mockProduct)).thenReturn(mockProduct);

        // When
        Product result = productService.createProduct(mockProduct);

        // Then
        assertEquals(mockProduct, result);
        verify(productRepository, times(1)).save(mockProduct);
    }

    @Test
    void testGetProductByIdExisting() throws ProductNotFoundException {
        // Given
        Long productId = 1L;
        Product mockProduct = new Product();
        when(productRepository.findById(productId)).thenReturn(Optional.of(mockProduct));

        // When
        Product result = productService.getProductById(productId);

        // Then
        assertEquals(mockProduct, result);
        verify(productRepository, times(1)).findById(productId);
    }

    @Test
    void testGetProductByIdNotExisting() {
        // Given
        Long productId = 1L;
        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        // When, Then
        assertThrows(ProductNotFoundException.class, () -> productService.getProductById(productId));
        verify(productRepository, times(1)).findById(productId);
    }


    @Test
    void testUpdateProductNotExisting() {
        // Given
        Long productId = 1L;
        Product updatedProduct = new Product();
        when(productRepository.existsById(productId)).thenReturn(false);

        // When, Then
        assertThrows(ProductNotFoundException.class, () -> productService.updateProduct(productId, updatedProduct));
        verify(productRepository, never()).save(any());
    }

    @Test
    void testDeleteProduct() {
        // Given
        Long productId = 1L;

        // When
        productService.deleteProduct(productId);

        // Then
        verify(productRepository, times(1)).deleteById(productId);
    }
}

