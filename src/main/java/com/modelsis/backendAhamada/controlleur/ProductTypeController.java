package com.modelsis.backendAhamada.controlleur;

import com.modelsis.backendAhamada.Exception.ProductNotFoundException;
import com.modelsis.backendAhamada.Exception.ProductTypeNotFoundException;
import com.modelsis.backendAhamada.Mappers.ProductTypeMapper;
import com.modelsis.backendAhamada.dto.ProductTypeDto;
import com.modelsis.backendAhamada.models.ProductType;
import com.modelsis.backendAhamada.service.ProductTypeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseEntity<List<ProductTypeDto>> getAllProductTypes() {
        log.info("Récupération de tous les types de produits.");
        List<ProductTypeDto> productTypeDtos = ProductTypeMapper.mapToProductTypeDtoList(productTypeService.getAllProductTypes());
        return ResponseEntity.ok(productTypeDtos);
    }

    /**
     * Crée un nouveau type de produit.
     *
     * @param productTypeDto Le type de produit à créer.
     * @return Une ResponseEntity contenant le type de produit créé.
     */
    @PostMapping
    public ResponseEntity<ProductTypeDto> createProductType(@RequestBody ProductTypeDto productTypeDto) {
        log.info("Création d'un nouveau type de produit : {}", productTypeDto);
        ProductType createdProductType = productTypeService.createProductType(ProductTypeMapper.mapToProductType(productTypeDto));
        return ResponseEntity.ok(ProductTypeMapper.mapToProductTypeDto(createdProductType));
    }

    /**
     * Récupère un type de produit par son identifiant.
     *
     * @param id L'identifiant du type de produit à récupérer.
     * @return Une ResponseEntity contenant le type de produit ou un message d'erreur si non trouvé.
     * @throws ProductTypeNotFoundException Si le type de produit n'est pas trouvé.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ProductTypeDto> getProductTypeById(@PathVariable Long id) throws ProductTypeNotFoundException {
        log.info("Récupération du type de produit avec l'identifiant : {}", id);
        ProductType productTypeRep = productTypeService.getProductTypeById(id);
        ProductTypeDto productTypeDto = ProductTypeMapper.mapToProductTypeDto(productTypeRep);
        return ResponseEntity.status(HttpStatus.FOUND).body(productTypeDto);
    }

    /**
     * Met à jour un type de produit existant.
     *
     * @param id                  L'identifiant du type de produit à mettre à jour.
     * @param updatedProductType Le type de produit mis à jour.
     * @return Une ResponseEntity contenant le type de produit mis à jour ou un statut NO_CONTENT si l'ID est nul.
     * @throws ProductTypeNotFoundException Si le type de produit n'est pas trouvé.
     * @throws ProductNotFoundException      Si le produit associé au type n'est pas trouvé.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ProductTypeDto> updateProductType(@PathVariable Long id, @RequestBody ProductTypeDto updatedProductType) throws ProductTypeNotFoundException, ProductNotFoundException {
        log.info("Mise à jour du type de produit avec l'identifiant : {}", id);

        if (id == null)
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

        ProductType updated = productTypeService.updateProductType(id, ProductTypeMapper.mapToProductType(updatedProductType));
        ProductTypeDto updatedProductTypeDto = ProductTypeMapper.mapToProductTypeDto(updated);
        return ResponseEntity.ok(updatedProductTypeDto);
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
