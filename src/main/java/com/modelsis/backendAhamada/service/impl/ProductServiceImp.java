package com.modelsis.backendAhamada.service.impl;

import com.modelsis.backendAhamada.Exception.ProductNotFoundException;
import com.modelsis.backendAhamada.Repository.ProductRepository;
import com.modelsis.backendAhamada.models.Product;
import com.modelsis.backendAhamada.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImp implements ProductService {
    private final Logger log = LoggerFactory.getLogger(ProductServiceImp.class);

    private final ProductRepository productRepository;

    public ProductServiceImp(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }
    /**
     * Récupère tous les produits.
     *
     * @return Une liste de produits.
     */
    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    /**
     * Récupère un produit par son identifiant.
     *
     * @param id L'identifiant du produit à récupérer.
     * @return Le produit correspondant à l'identifiant.
     * @throws ProductNotFoundException Si le produit n'est pas trouvé.
     */
    @Override
    public Product getProductById(Long id) throws ProductNotFoundException {
        Optional<Product> productRep = productRepository.findById(id);
        if (productRep.isEmpty()) {
            throw new ProductNotFoundException("Produit non trouvé avec l'ID : " + id);
        }
        return productRep.get();
    }
    /**
     * Crée un nouveau produit.
     *
     * @param product Le produit à créer.
     * @return Le produit créé.
     */

    @Override
    public Product createProduct(Product product) {
        product.setCreatedAt(LocalDateTime.now());
        return productRepository.save(product);
    }
    /**
     * Met à jour un produit existant.
     *
     * @param id            L'identifiant du produit à mettre à jour.
     * @param updatedProduct Les nouvelles informations du produit.
     * @return Le produit mis à jour.
     * @throws ProductNotFoundException Si le produit n'est pas trouvé.
     */
    @Override
    public Product updateProduct(Long id, Product updatedProduct) throws ProductNotFoundException {
        if (productRepository.existsById(id)) {

            Product product = new Product();
            product.setId(id);
            product.setUpdatedAt(updatedProduct.getUpdatedAt());
            product.setCreatedAt(LocalDateTime.now());
            return productRepository.save(product);
        } else {
            throw new ProductNotFoundException("Produit non trouvé avec l'ID : " + id);

        }
    }
    /**
     * Supprime un produit par son identifiant.
     *
     * @param id L'identifiant du produit à supprimer.
     */
    @Override
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
}
