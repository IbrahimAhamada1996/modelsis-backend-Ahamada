package com.modelsis.backendAhamada.service.impl;

import com.modelsis.backendAhamada.Exception.ProductNotFoundException;
import com.modelsis.backendAhamada.Exception.ProductTypeNotFoundException;
import com.modelsis.backendAhamada.Repository.ProductTypeRepository;
import com.modelsis.backendAhamada.controlleur.ProductTypeController;
import com.modelsis.backendAhamada.models.ProductType;
import com.modelsis.backendAhamada.service.ProductTypeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ProductTypeServiceImp implements ProductTypeService {
    private final Logger log = LoggerFactory.getLogger(ProductTypeServiceImp.class);

    private final ProductTypeRepository productTypeRepository;

    public ProductTypeServiceImp(ProductTypeRepository productTypeRepository) {
        this.productTypeRepository = productTypeRepository;
    }
    /**
     * Récupère tous les types de produits.
     *
     * @return Une liste de types de produits.
     */
    @Override
    public List<ProductType> getAllProductTypes() {
        return productTypeRepository.findAll();
    }
    /**
     * Récupère un type de produit par son identifiant.
     *
     * @param id L'identifiant du type de produit à récupérer.
     * @return Le type de produit correspondant à l'identifiant.
     * @throws ProductTypeNotFoundException Si le type de produit n'est pas trouvé.
     */
    @Override
    public ProductType getProductTypeById(Long id) throws ProductTypeNotFoundException {
        Optional<ProductType> productTypeRep = productTypeRepository.findById(id);
        if (productTypeRep.isEmpty()) {
            throw new ProductTypeNotFoundException("Produit non trouvé avec l'ID : " + id);
        }
        return productTypeRep.get();
    }
    /**
     * Crée un nouveau type de produit.
     *
     * @param productType Le type de produit à créer.
     * @return Le type de produit créé.
     */
    @Override
    public ProductType createProductType(ProductType productType) {
        productType.setCreatedAt(LocalDateTime.now());
        return productTypeRepository.save(productType);
    }
    /**
     * Met à jour un type de produit existant.
     *
     * @param id                L'identifiant du type de produit à mettre à jour.
     * @param updatedProductType Les nouvelles informations du type de produit.
     * @return Le type de produit mis à jour.
     * @throws ProductTypeNotFoundException Si le type de produit n'est pas trouvé.
     */
    @Override
    public ProductType updateProductType(Long id, ProductType updatedProductType) throws ProductTypeNotFoundException {
        ProductType productTypeRep = this.getProductTypeById(id);

        if (productTypeRep!=null) {
            productTypeRep.setName(updatedProductType.getName());
            productTypeRep.setUpdatedAt(LocalDateTime.now());
            return productTypeRepository.save(productTypeRep);
        } else {
            throw new ProductTypeNotFoundException("Produit non trouvé avec l'ID : " + id);

        }
    }
    /**
     * Supprime un type de produit par son identifiant.
     *
     * @param id L'identifiant du type de produit à supprimer.
     */
    @Override
    public void deleteProductType(Long id) {
        productTypeRepository.deleteById(id);
    }

}
