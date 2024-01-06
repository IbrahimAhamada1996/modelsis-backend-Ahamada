package com.modelsis.backendAhamada.controlleur;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.modelsis.backendAhamada.Mappers.ProductMapper;
import com.modelsis.backendAhamada.Mappers.ProductTypeMapper;
import com.modelsis.backendAhamada.Repository.ProductRepository;
import com.modelsis.backendAhamada.dto.ProductDto;
import com.modelsis.backendAhamada.models.Product;
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
public class ProductControlleurTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private ProductRepository productRepository;

    private ProductDto productDto;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductDto createEntityDto() {
        return ProductDto.builder()
                .name("Ordinateur")
                .createdAt(LocalDateTime.now())
                .build();
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductDto createUpdatedEntityDto() {
        return ProductDto.builder()
                .id(1L)
                .name("PC")
                .updatedAt(LocalDateTime.now())
                .build();
    }

    @BeforeEach
    public void initTest() {
        this.productDto = createEntityDto();
    }

    @Test
    void testCreateProduct() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productDto)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Ordinateur"));
    }

    @Test
    void testGetAllProducts() throws Exception {
       Product product =  productRepository.saveAndFlush(ProductMapper.mapToProduct(productDto));
        this.productDto = ProductMapper.mapToProductDto(product);
        mockMvc.perform(MockMvcRequestBuilders.get("/products"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(productDto.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(productDto.getName())))
        ;
    }

    @Test
    void testGetProductById() throws Exception {
      Product  product=   productRepository.saveAndFlush(ProductMapper.mapToProduct(productDto));
        this.productDto = ProductMapper.mapToProductDto(product);
        mockMvc.perform(MockMvcRequestBuilders.get("/products/{id}", productDto.getId()))
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(productDto.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(productDto.getName()));

    }

    @Test
    void testUpdateProduct() throws Exception {
        productDto = ProductMapper.mapToProductDto(productRepository.saveAndFlush(ProductMapper.mapToProduct(createUpdatedEntityDto())));

        mockMvc.perform(MockMvcRequestBuilders.put("/products/{id}", productDto.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productDto)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("PC"));
    }

    @Test
    void testDeleteProduct() throws Exception {
        productDto = ProductMapper.mapToProductDto(productRepository.saveAndFlush(ProductMapper.mapToProduct(createEntityDto())));

        mockMvc.perform(MockMvcRequestBuilders.delete("/products/{id}", productDto.getId()))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }
}
