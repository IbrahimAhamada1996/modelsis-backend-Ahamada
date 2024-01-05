package com.modelsis.backendAhamada.config;

import com.modelsis.backendAhamada.controlleur.ProductController;
import com.modelsis.backendAhamada.controlleur.ProductTypeController;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@Configuration
public class TestConfig {

    @Bean
    public MockMvc mockMvc(ProductController productController, ProductTypeController productTypeController) {
        return MockMvcBuilders.standaloneSetup(productController, productTypeController).build();
    }
}
