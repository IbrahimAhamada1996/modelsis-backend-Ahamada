package com.modelsis.backendAhamada;


import com.modelsis.backendAhamada.models.Product;
import com.modelsis.backendAhamada.models.ProductType;
import com.modelsis.backendAhamada.service.ProductService;
import com.modelsis.backendAhamada.service.ProductTypeService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@SpringBootApplication
public class BackendAhamadaApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackendAhamadaApplication.class, args);
	}
	@Bean
	public CommandLineRunner demoData(ProductTypeService productTypeService, ProductService productService) {
		return args -> {
			// Création de 5 types de produits
			for (int i = 1; i <= 5; i++) {
				ProductType productType = new ProductType();
				productType.setName("Type de produit " + i);
				productType.setCreatedAt(LocalDateTime.now());
				productTypeService.createProductType(productType);
			}

			// Création de 10 produits
			for (int i = 1; i <= 10; i++) {
				Product product = new Product();
				product.setName("Produit " + i);
				product.setCreatedAt(LocalDateTime.now());

				// Sélection aléatoire d'un type de produit existant
				List<ProductType> allProductTypes = productTypeService.getAllProductTypes();
				if (!allProductTypes.isEmpty()) {
					Random random = new Random();
					int randomIndex = random.nextInt(allProductTypes.size());
					ProductType randomProductType = allProductTypes.get(randomIndex);
					product.setProductType(randomProductType);
				}

				productService.createProduct(product);
			}
		};
	}
}
