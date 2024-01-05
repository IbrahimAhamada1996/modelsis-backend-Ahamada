package com.modelsis.backendAhamada.controlleur;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.modelsis.backendAhamada.Repository.ProductRepository;
import com.modelsis.backendAhamada.Repository.ProductTypeRepository;
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
    private Product product;
    @Autowired
    private ProductRepository productRepository;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Product createEntity(EntityManager em) {
        Product product = new Product();
//                product.setId(1L);
                product.setName("Ordinateur");
                product.setCreatedAt(LocalDateTime.now());
    return product;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Product createUpdatedEntity(EntityManager em) {
        Product product = new Product();
        product.setId(1L);
        product.setName("PC");
        product.setUpdatedAt(LocalDateTime.now());
        return product;
    }

    @BeforeEach
    public void initTest() {
        this.product = createEntity(em);
    }

    @Test
    void testCreateProduct() throws Exception {

        productRepository.saveAndFlush(this.product);

        mockMvc.perform(MockMvcRequestBuilders.post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(product)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Ordinateur"));
    }


    @Test
    void testGetAllProducts() throws Exception {
        productRepository.saveAndFlush(this.product);
        mockMvc.perform(MockMvcRequestBuilders.get("/products"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(this.product.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(this.product.getName())))
        ;
    }


    @Test
    void testGetProductById() throws Exception {
        Long productId = 1L;
        productRepository.saveAndFlush(this.product);


        mockMvc.perform(MockMvcRequestBuilders.get("/products/{id}", this.product.getId()))
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(this.product.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(this.product.getName()));

    }

    @Test
    void testUpdateProduct() throws Exception {
        Long productId = 1L;

        this.product = productRepository.saveAndFlush(createUpdatedEntity(em)); ;

        mockMvc.perform(MockMvcRequestBuilders.put("/products/{id}", productId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(product)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("PC"));
    }

    @Test
    void testDeleteProduct() throws Exception {
        Long productId = 1L;

        mockMvc.perform(MockMvcRequestBuilders.delete("/products/{id}", productId))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

}



