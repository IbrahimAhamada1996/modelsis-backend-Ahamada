package com.modelsis.backendAhamada.Service;
import com.modelsis.backendAhamada.Exception.ProductTypeNotFoundException;
import com.modelsis.backendAhamada.Repository.ProductTypeRepository;
import com.modelsis.backendAhamada.models.ProductType;
import com.modelsis.backendAhamada.service.impl.ProductTypeServiceImp;
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
public class ProductTypeServiceTest {

    @Mock
    private ProductTypeRepository productTypeRepository;

    @InjectMocks
    private ProductTypeServiceImp productTypeService;

    @Test
    void testGetAllProductTypes() {
        // Given
        List<ProductType> mockProductTypes = new ArrayList<>();
        when(productTypeRepository.findAll()).thenReturn(mockProductTypes);

        // When
        List<ProductType> result = productTypeService.getAllProductTypes();

        // Then
        assertEquals(mockProductTypes, result);
        verify(productTypeRepository, times(1)).findAll();
    }

    @Test
    void testGetProductTypeByIdExisting() throws ProductTypeNotFoundException {
        // Given
        Long productTypeId = 1L;
        ProductType mockProductType = new ProductType();
        when(productTypeRepository.findById(productTypeId)).thenReturn(Optional.of(mockProductType));

        // When
        ProductType result = productTypeService.getProductTypeById(productTypeId);

        // Then
        assertEquals(mockProductType, result);
        verify(productTypeRepository, times(1)).findById(productTypeId);
    }

    @Test
    void testGetProductTypeByIdNotExisting() {
        // Given
        Long productTypeId = 1L;
        when(productTypeRepository.findById(productTypeId)).thenReturn(Optional.empty());

        // When, Then
        assertThrows(ProductTypeNotFoundException.class, () -> productTypeService.getProductTypeById(productTypeId));
        verify(productTypeRepository, times(1)).findById(productTypeId);
    }

    @Test
    void testCreateProductType() {
        // Given
        ProductType mockProductType = new ProductType();
        mockProductType.setName("Test ProductType");
        mockProductType.setCreatedAt(LocalDateTime.now());
        when(productTypeRepository.save(mockProductType)).thenReturn(mockProductType);

        // When
        ProductType result = productTypeService.createProductType(mockProductType);

        // Then
        assertEquals(mockProductType, result);
        verify(productTypeRepository, times(1)).save(mockProductType);
    }

    @Test
    void testUpdateProductTypeExisting() throws ProductTypeNotFoundException {
        // Given
        Long productTypeId = 1L;
        ProductType updatedProductType = new ProductType();
        updatedProductType.setName("Updated ProductType");
        when(productTypeRepository.existsById(productTypeId)).thenReturn(true);
        when(productTypeRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        // When
        ProductType result = productTypeService.updateProductType(productTypeId, updatedProductType);

        // Then
        assertEquals(updatedProductType.getName(), result.getName());
        assertNotNull(result.getUpdatedAt());
        verify(productTypeRepository, times(1)).save(any());
    }

    @Test
    void testUpdateProductTypeNotExisting() {
        // Given
        Long productTypeId = 1L;
        ProductType updatedProductType = new ProductType();
        when(productTypeRepository.existsById(productTypeId)).thenReturn(false);

        // When, Then
        assertThrows(ProductTypeNotFoundException.class, () -> productTypeService.updateProductType(productTypeId, updatedProductType));
        verify(productTypeRepository, never()).save(any());
    }

    @Test
    void testDeleteProductType() {
        // Given
        Long productTypeId = 1L;

        // When
        productTypeService.deleteProductType(productTypeId);

        // Then
        verify(productTypeRepository, times(1)).deleteById(productTypeId);
    }
}

