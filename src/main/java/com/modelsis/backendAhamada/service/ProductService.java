package com.modelsis.backendAhamada.service;

import com.modelsis.backendAhamada.Exception.ProductNotFoundException;
import com.modelsis.backendAhamada.Repository.ProductRepository;
import com.modelsis.backendAhamada.models.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {

    /**
     * Récupère tous les produits.
     *
     * @return Une liste de produits.
     */
    List<Product> getAllProducts();

    /**
     * Récupère un produit par son identifiant.
     *
     * @param id L'identifiant du produit à récupérer.
     * @return Le produit correspondant à l'identifiant.
     * @throws ProductNotFoundException Si le produit n'est pas trouvé.
     */
    Product getProductById(Long id) throws ProductNotFoundException;

    /**
     * Crée un nouveau produit.
     *
     * @param product Le produit à créer.
     * @return Le produit créé.
     */
    Product createProduct(Product product);

    /**
     * Met à jour un produit existant.
     *
     * @param id              L'identifiant du produit à mettre à jour.
     * @param updatedProduct Les nouvelles informations du produit.
     * @return Le produit mis à jour.
     * @throws ProductNotFoundException Si le produit n'est pas trouvé.
     */
    Product updateProduct(Long id, Product updatedProduct) throws ProductNotFoundException;

    /**
     * Supprime un produit par son identifiant.
     *
     * @param id L'identifiant du produit à supprimer.
     */
    void deleteProduct(Long id);
}
