package com.modelsis.backendAhamada.Mappers;

import com.modelsis.backendAhamada.dto.ProductTypeDto;
import com.modelsis.backendAhamada.models.ProductType;

import java.util.List;
import java.util.stream.Collectors;

public class ProductTypeMapper {
    public static ProductTypeDto mapToProductTypeDto(ProductType productType) {
        if (productType == null) {
            return null;
        }

        return ProductTypeDto.builder()
                .id(productType.getId())
                .name(productType.getName())
                .createdAt(productType.getCreatedAt())
                .updatedAt(productType.getUpdatedAt())
                .build();
    }

    public static ProductType mapToProductType(ProductTypeDto productTypeDto) {
        if (productTypeDto == null) {
            return null;
        }

        return ProductType.builder()
                .id(productTypeDto.getId())
                .name(productTypeDto.getName())
                .createdAt(productTypeDto.getCreatedAt())
                .updatedAt(productTypeDto.getUpdatedAt())
                .build();
    }

    public static List<ProductTypeDto> mapToProductTypeDtoList(List<ProductType> productTypeList) {
        return productTypeList.stream()
                .map(ProductTypeMapper::mapToProductTypeDto)
                .collect(Collectors.toList());
    }
}
