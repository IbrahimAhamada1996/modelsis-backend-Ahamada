package com.modelsis.backendAhamada.controlleur;

import com.modelsis.backendAhamada.Exception.ProductNotFoundException;
import com.modelsis.backendAhamada.Exception.ProductTypeNotFoundException;
import com.modelsis.backendAhamada.models.ProductType;
import com.modelsis.backendAhamada.service.ProductTypeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/productTypes")
public class ProductTypeController {
    private final Logger log = LoggerFactory.getLogger(ProductTypeController.class);

    private final ProductTypeService productTypeService;

    public ProductTypeController(ProductTypeService productTypeService) {
        this.productTypeService = productTypeService;
    }
    /**
     * Récupère tous les types de produits.
     *
     * @return Une liste de types de produits.
     */
    @GetMapping
    public ResponseEntity<List<ProductType>> getAllProductTypes() {
        log.info("Récupération de tous les types de produits.");

        return ResponseEntity.ok(productTypeService.getAllProductTypes());
    }

    /**
     * Crée un nouveau type de produit.
     *
     * @param productType Le type de produit à créer.
     * @return Une ResponseEntity contenant le type de produit créé.
     */
    @PostMapping
    public ResponseEntity<ProductType> createProductType(@RequestBody ProductType productType) {
        log.info("Création d'un nouveau type de produit : {}", productType);

        ProductType createdProductType = productTypeService.createProductType(productType);
        return ResponseEntity.ok(createdProductType);
    }
    /**
     * Récupère un type de produit par son identifiant.
     *
     * @param id L'identifiant du type de produit à récupérer.
     * @return Une ResponseEntity contenant le type de produit ou un message d'erreur si non trouvé.
     * @throws ProductTypeNotFoundException Si le type de produit n'est pas trouvé.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ProductType> getProductTypeById(@PathVariable Long id) throws ProductTypeNotFoundException {
        log.info("Récupération du type de produit avec l'identifiant : {}", id);

        ProductType productTypeRep = productTypeService.getProductTypeById(id);
    return ResponseEntity.status(HttpStatus.FOUND).body(productTypeRep);
    }
    /**
     * Met à jour un type de produit existant.
     *
     * @param id L'identifiant du type de produit à mettre à jour.
     * @param updatedProductType Le type de produit mis à jour.
     * @return Une ResponseEntity contenant le type de produit mis à jour ou un statut NO_CONTENT si l'ID est nul.
     * @throws ProductTypeNotFoundException Si le type de produit n'est pas trouvé.
     * @throws ProductNotFoundException Si le produit associé au type n'est pas trouvé.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ProductType> updateProductType(@PathVariable Long id, @RequestBody ProductType updatedProductType) throws ProductTypeNotFoundException, ProductNotFoundException {
        log.info("Mise à jour du type de produit avec l'identifiant : {}", id);

        if (id ==null)
            return new ResponseEntity<ProductType>(HttpStatus.NO_CONTENT);

        ProductType updated = productTypeService.updateProductType(id, updatedProductType);
        return ResponseEntity.ok(updated);
    }
    /**
     * Supprime un type de produit par son identifiant.
     *
     * @param id L'identifiant du type de produit à supprimer.
     * @return Une ResponseEntity avec le statut NO_CONTENT après la suppression.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProductType(@PathVariable Long id) {
        log.info("Suppression du type de produit avec l'identifiant : {}", id);

        productTypeService.deleteProductType(id);
        return ResponseEntity.noContent().build();
    }
}

