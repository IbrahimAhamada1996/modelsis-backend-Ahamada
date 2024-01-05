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
public class ProductTypeServiceImplTest {

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
    void testUpdateProductType() throws ProductTypeNotFoundException {
        // Given
        Long productTypeId = 1L;
        String updatedProductTypeName = "Updated ProductType";
        ProductType existingProductType = ProductType.builder()
                .id(productTypeId)
                .name("Original ProductType")
                .build();
        ProductType updatedProductType = ProductType.builder()
                .name(updatedProductTypeName)
                .build();

        // Mock repository behavior
        when(productTypeRepository.findById(productTypeId)).thenReturn(Optional.of(existingProductType));
        when(productTypeRepository.save(any(ProductType.class))).thenAnswer(invocation -> {
            ProductType savedProductType = invocation.getArgument(0);
            return savedProductType;
        });

        // When
        ProductType result = productTypeService.updateProductType(productTypeId, updatedProductType);

        // Then
        assertNotNull(result);
        assertEquals(updatedProductTypeName, result.getName());
    }

    @Test
    void testUpdateProductType_ProductTypeNotFound() {
        // Given
        Long productTypeId = 1L;
        ProductType updatedProductType = ProductType.builder().name("Updated ProductType").build();

        // Mock repository behavior
        when(productTypeRepository.findById(productTypeId)).thenReturn(Optional.empty());

        // When and Then
        assertThrows(ProductTypeNotFoundException.class, () -> productTypeService.updateProductType(productTypeId, updatedProductType));
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

