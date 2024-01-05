package com.modelsis.backendAhamada.controlleur;

import com.modelsis.backendAhamada.Exception.ProductNotFoundException;
import com.modelsis.backendAhamada.models.Product;
import com.modelsis.backendAhamada.models.ProductType;
import com.modelsis.backendAhamada.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/products")
public class ProductController {
    private final Logger log = LoggerFactory.getLogger(ProductController.class);

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }
    /**
     * Récupère tous les produits.
     *
     * @return Une liste de produits.
     */
    @GetMapping
    public ResponseEntity<List<Product>>  getAllProducts() {
        log.info("Récupération de tous les produits.");
        return ResponseEntity.ok(productService.getAllProducts());
    }

    /**
     * Crée un nouveau produit.
     *
     * @param product Le produit à créer.
     * @return Le produit créé.
     */
    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        log.info("Création d'un nouveau produit : {}", product);
        Product createdProduct = productService.createProduct(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdProduct);
    }

    /**
     * Récupère un produit par son identifiant.
     *
     * @param id L'identifiant du produit à récupérer.
     * @return Une ResponseEntity contenant le produit ou un message d'erreur si non trouvé.
     * @throws ProductNotFoundException Si le produit n'est pas trouvé.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductTypeById(@PathVariable Long id) throws ProductNotFoundException {
        log.info("Récupération du produit avec l'identifiant : {}", id);
        Product product = productService.getProductById(id);
        return ResponseEntity.status(HttpStatus.FOUND).body(product);
    }

    /**
     * Met à jour un produit existant.
     *
     * @param product Le produit mis à jour.
     * @param id L'identifiant du produit à mettre à jour.
     * @return Une ResponseEntity contenant le produit mis à jour ou un statut NO_CONTENT si l'ID est nul.
     * @throws ProductNotFoundException Si le produit n'est pas trouvé.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@RequestBody Product product, @PathVariable Long id) throws ProductNotFoundException {
        log.info("Mise à jour du produit avec l'identifiant : {}", id);

        if (id == null)
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

        Product updatedProduct = productService.updateProduct(id, product);
        return ResponseEntity.ok(updatedProduct);
    }

    /**
     * Supprime un produit par son identifiant.
     *
     * @param id L'identifiant du produit à supprimer.
     * @return Une ResponseEntity avec le statut NO_CONTENT après la suppression.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProductType(@PathVariable Long id) {
        log.info("Suppression du produit avec l'identifiant : {}", id);
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

}

