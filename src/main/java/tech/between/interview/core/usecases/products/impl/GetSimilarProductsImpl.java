package tech.between.interview.core.usecases.products.impl;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.between.interview.core.entities.ProductDescription;
import tech.between.interview.core.usecases.exceptions.UseCaseException;
import tech.between.interview.core.usecases.products.CacheProducts;
import tech.between.interview.core.usecases.products.GetDescription;
import tech.between.interview.core.usecases.products.GetSimilarIds;
import tech.between.interview.core.usecases.products.GetSimilarProducts;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class GetSimilarProductsImpl implements GetSimilarProducts {

    private final CacheProducts cacheProducts;
    private final GetSimilarIds getSimilarIds;
    private final GetDescription getDescription;

    @Autowired
    public GetSimilarProductsImpl(final CacheProducts cacheProducts,
                                  final GetSimilarIds getSimilarIds,
                                  final GetDescription getDescription) {
        this.cacheProducts = cacheProducts;
        this.getSimilarIds = getSimilarIds;
        this.getDescription = getDescription;
    }

    @Override
    public List<ProductDescription> apply(@NonNull final Integer productId) {
        final Set<Integer> similarIds = getSimilarIds.apply(productId);

        final List<ProductDescription> productDescriptionList = similarIds.stream()
                .map(this::getDescriptionService)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());

        cacheProducts(similarIds);

        return productDescriptionList;
    }

    private void cacheProducts(final Set<Integer> similarIds) {
        final Set<Integer> childProducts = similarIds.stream()
                .parallel()
                .map(this::getSimilarIdsService)
                .flatMap(integers -> integers.stream().filter(productId -> !similarIds.contains(productId)))
                .collect(Collectors.toSet());

        cacheProducts.accept(childProducts);
    }

    private Optional<ProductDescription> getDescriptionService(final int productId) {
        try {
            return Optional.ofNullable(getDescription.apply(productId));
        } catch (UseCaseException ignored) {
        }
        return Optional.empty();
    }

    private Set<Integer> getSimilarIdsService(final int productId) {
        try {
            return getSimilarIds.apply(productId);
        } catch (UseCaseException ignored) {
        }
        return Collections.emptySet();
    }
}
