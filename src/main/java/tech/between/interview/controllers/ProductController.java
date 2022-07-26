package tech.between.interview.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.between.interview.core.entities.ProductDescription;
import tech.between.interview.core.usecases.products.GetDescription;
import tech.between.interview.core.usecases.products.GetSimilarIds;
import tech.between.interview.core.usecases.products.GetSimilarProducts;

import java.util.List;
import java.util.Set;

import static tech.between.interview.configuration.cache.CacheConfig.SIMILAR_PRODUCT;

@RestController
@RequestMapping("/product")
public class ProductController {
    private final GetSimilarProducts getSimilarProducts;
    private final GetDescription getDescription;
    private final GetSimilarIds getSimilarIds;

    @Autowired
    public ProductController(GetSimilarProducts getSimilarProducts, GetSimilarIds getSimilarIds, GetDescription getDescription) {
        this.getSimilarIds = getSimilarIds;
        this.getSimilarProducts = getSimilarProducts;
        this.getDescription = getDescription;
    }

    @GetMapping("/{id}/similarids")
    public ResponseEntity<Set<Integer>> getSimilarIds(@PathVariable("id") int id) {
        return ResponseEntity.ok(this.getSimilarIds.apply(id));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDescription> getProduct(@PathVariable("id") int id) {
        return ResponseEntity.ok(this.getDescription.apply(id));
    }

    @GetMapping("/{id}/similar")
    @Cacheable(SIMILAR_PRODUCT)
    public ResponseEntity<List<ProductDescription>> getProductsSimilar(@PathVariable("id") int id) {
        return ResponseEntity.ok(this.getSimilarProducts.apply(id));
    }

}
