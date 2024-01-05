package com.modelsis.backendAhamada.controlleur;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.modelsis.backendAhamada.Repository.ProductTypeRepository;
import com.modelsis.backendAhamada.models.Product;
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
    private ProductType productType;
    @Autowired
    private ProductTypeRepository productTypeRepository;
    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductType createEntity(EntityManager em) {
        ProductType productType = new ProductType();
//        productType.setId(1L);
        productType.setName("Vêtements");
        productType.setCreatedAt(LocalDateTime.now());
        return productType;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductType createUpdatedEntity(EntityManager em) {
        ProductType productType = new ProductType();
//        productType.setId(1L);
        productType.setName("Électronique");
        productType.setUpdatedAt(LocalDateTime.now());
        return productType;
    }

    @BeforeEach
    public void initTest() {
        this.productType = createEntity(em);
    }

    @Test
    void testCreateProductTypeType() throws Exception {

//        ProductType productType = new ProductType();
//        productType.setName("Test ProductTypeType");
        productTypeRepository.saveAndFlush(this.productType);

        mockMvc.perform(MockMvcRequestBuilders.post("/productTypes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productType)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.name").value("Vêtements"));
    }

    @Test
    void testGetAllProductTypes() throws Exception {
       productTypeRepository.saveAndFlush(this.productType);

        mockMvc.perform(MockMvcRequestBuilders.get("/productTypes"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(this.productType.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(this.productType.getName())))
        ;
    }



    @Test
    void testGetProductTypeById() throws Exception {
//        Long productTypeId = 1L;
        productTypeRepository.saveAndFlush(this.productType);
        mockMvc.perform(MockMvcRequestBuilders.get("/productTypes/{id}", this.productType.getId()))
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(this.productType.getId()))
                .andExpect(jsonPath("$.name").value(this.productType.getName()))

        ;

    }

    @Test
    void testUpdateProductType() throws Exception {
        Long productTypeId = 1L;
//        ProductType updatedProductTypeType = new ProductType();
//        updatedProductTypeType.setName("Updated ProductTypeType");

        this.productType = productTypeRepository.saveAndFlush(createUpdatedEntity(em));


        mockMvc.perform(MockMvcRequestBuilders.put("/productTypes/{id}", productTypeId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productType)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.name").value("Électronique"));
    }

    @Test
    void testDeleteProductType() throws Exception {
        Long productTypeId = 1L;

        mockMvc.perform(MockMvcRequestBuilders.delete("/productTypes/{id}", productTypeId))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }
}
