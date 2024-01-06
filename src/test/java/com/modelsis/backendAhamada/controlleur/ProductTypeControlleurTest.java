package com.modelsis.backendAhamada.controlleur;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.modelsis.backendAhamada.Mappers.ProductTypeMapper;
import com.modelsis.backendAhamada.Repository.ProductTypeRepository;
import com.modelsis.backendAhamada.dto.ProductTypeDto;
import com.modelsis.backendAhamada.models.ProductType;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDateTime;

import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
public class ProductTypeControlleurTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private ProductTypeRepository productTypeRepository;

    private ProductTypeDto productTypeDto;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductTypeDto createEntityDto() {
        return ProductTypeDto.builder()
                .name("Vêtements")
                .createdAt(LocalDateTime.now())
                .build();
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductTypeDto createUpdatedEntityDto() {
        return ProductTypeDto.builder()
                .id(1L)
                .name("Électronique")
                .updatedAt(LocalDateTime.now())
                .build();
    }

    @BeforeEach
    public void initTest() {
        this.productTypeDto = createEntityDto();
    }

    @Test
    void testCreateProductType() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/productTypes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productTypeDto)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.name").value("Vêtements"));
    }

    @Test
    void testGetAllProductTypes() throws Exception {
     ProductType productType =    productTypeRepository.saveAndFlush(ProductTypeMapper.mapToProductType(productTypeDto));
    this.productTypeDto = ProductTypeMapper.mapToProductTypeDto(productType);
        mockMvc.perform(MockMvcRequestBuilders.get("/productTypes"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(productTypeDto.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(productTypeDto.getName())))
        ;
    }

    @Test
    void testGetProductTypeById() throws Exception {
        ProductType productType =   productTypeRepository.saveAndFlush(ProductTypeMapper.mapToProductType(productTypeDto));
        this.productTypeDto = ProductTypeMapper.mapToProductTypeDto(productType);

        mockMvc.perform(MockMvcRequestBuilders.get("/productTypes/{id}", productTypeDto.getId()))
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(productTypeDto.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(productTypeDto.getName()));
    }

    @Test
    void testUpdateProductType() throws Exception {
        productTypeDto = ProductTypeMapper.mapToProductTypeDto(productTypeRepository.saveAndFlush(ProductTypeMapper.mapToProductType(createUpdatedEntityDto())));

        mockMvc.perform(MockMvcRequestBuilders.put("/productTypes/{id}", productTypeDto.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productTypeDto)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.name").value("Électronique"));
    }

    @Test
    void testDeleteProductType() throws Exception {
        productTypeDto = ProductTypeMapper.mapToProductTypeDto(productTypeRepository.saveAndFlush(ProductTypeMapper.mapToProductType(createEntityDto())));

        mockMvc.perform(MockMvcRequestBuilders.delete("/productTypes/{id}", productTypeDto.getId()))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }
}
