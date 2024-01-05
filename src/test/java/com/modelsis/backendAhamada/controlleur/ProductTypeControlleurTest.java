package com.modelsis.backendAhamada.controlleur;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.modelsis.backendAhamada.models.ProductType;
import com.modelsis.backendAhamada.service.ProductTypeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
@SpringBootTest
@AutoConfigureMockMvc
public class ProductTypeControlleurTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ProductTypeService productTypeService;

    @Test
    void testGetAllProductTypes() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/productTypes"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void testCreateProductType() throws Exception {
        ProductType productType = new ProductType();
        productType.setName("Test ProductType");

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/productTypes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productType)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Test ProductType"));
    }

    @Test
    void testGetProductTypeById() throws Exception {
        Long productTypeId = 1L;

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/productTypes/{id}", productTypeId))
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(productTypeId));
    }

    @Test
    void testUpdateProductType() throws Exception {
        Long productTypeId = 1L;
        ProductType updatedProductType = new ProductType();
        updatedProductType.setName("Updated ProductType");

        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/productTypes/{id}", productTypeId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedProductType)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Updated ProductType"));
    }

    @Test
    void testDeleteProductType() throws Exception {
        Long productTypeId = 1L;

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/productTypes/{id}", productTypeId))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }
}
