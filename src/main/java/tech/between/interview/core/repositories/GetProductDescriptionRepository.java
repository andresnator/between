package tech.between.interview.core.repositories;

import tech.between.interview.core.entities.ProductDescription;

import java.util.Optional;

@FunctionalInterface
public interface GetProductDescriptionRepository {
    Optional<ProductDescription> getDescription(int productId);
}
