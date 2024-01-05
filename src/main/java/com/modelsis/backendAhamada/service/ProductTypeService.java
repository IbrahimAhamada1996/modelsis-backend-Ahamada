package com.modelsis.backendAhamada.service;

import com.modelsis.backendAhamada.Exception.ProductNotFoundException;
import com.modelsis.backendAhamada.Exception.ProductTypeNotFoundException;
import com.modelsis.backendAhamada.models.ProductType;

import java.util.List;
import java.util.Optional;

public interface ProductTypeService {

    /**
     * Récupère tous les types de produits.
     *
     * @return Une liste des types de produits.
     */
    List<ProductType> getAllProductTypes();

    /**
     * Récupère un type de produit par son identifiant.
     *
     * @param id L'identifiant du type de produit à récupérer.
     * @return Le type de produit correspondant à l'identifiant.
     * @throws ProductTypeNotFoundException Si le type de produit n'est pas trouvé.
     */
    ProductType getProductTypeById(Long id) throws ProductTypeNotFoundException;

    /**
     * Crée un nouveau type de produit.
     *
     * @param productType Le type de produit à créer.
     * @return Le type de produit créé.
     */
    ProductType createProductType(ProductType productType);

    /**
     * Met à jour un type de produit existant.
     *
     * @param id                  L'identifiant du type de produit à mettre à jour.
     * @param updatedProductType Les nouvelles informations du type de produit.
     * @return Le type de produit mis à jour.
     * @throws ProductNotFoundException, ProductTypeNotFoundException Si le produit ou le type de produit n'est pas trouvé.
     */
    ProductType updateProductType(Long id, ProductType updatedProductType) throws ProductNotFoundException, ProductTypeNotFoundException;

    /**
     * Supprime un type de produit par son identifiant.
     *
     * @param id L'identifiant du type de produit à supprimer.
     */
    void deleteProductType(Long id);
}
