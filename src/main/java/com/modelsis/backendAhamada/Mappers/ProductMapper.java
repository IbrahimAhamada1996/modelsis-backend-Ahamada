package com.modelsis.backendAhamada.Mappers;

import com.modelsis.backendAhamada.dto.ProductDto;
import com.modelsis.backendAhamada.models.Product;

import java.util.List;
import java.util.stream.Collectors;

public class ProductMapper {

    public static ProductDto mapToProductDto(Product product) {
        if (product == null) {
            return null;
        }

        return ProductDto.builder()
                .id(product.getId())
                .name(product.getName())
                .createdAt(product.getCreatedAt())
                .updatedAt(product.getUpdatedAt())
                .productType(ProductTypeMapper.mapToProductTypeDto(product.getProductType()))
                .build();
    }

    public static Product mapToProduct(ProductDto productDto) {
        if (productDto == null) {
            return null;
        }

        return Product.builder()
                .id(productDto.getId())
                .name(productDto.getName())
                .createdAt(productDto.getCreatedAt())
                .updatedAt(productDto.getUpdatedAt())
                .productType(ProductTypeMapper.mapToProductType(productDto.getProductType()))
                .build();
    }

    public static List<ProductDto> mapToProductDtoList(List<Product> productList) {
        return productList.stream()
                .map(ProductMapper::mapToProductDto)
                .collect(Collectors.toList());
    }
}
