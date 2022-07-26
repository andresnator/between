package tech.between.interview.core.usecases.products.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.between.interview.core.usecases.exceptions.UseCaseException;
import tech.between.interview.core.usecases.products.CacheProducts;
import tech.between.interview.core.usecases.products.GetDescription;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

@Service
public class CacheProductsImpl implements CacheProducts {

    private final GetDescription getDescription;

    @Autowired
    public CacheProductsImpl(final GetDescription getDescription) {
        this.getDescription = getDescription;
    }

    @Override
    public void accept(final Set<Integer> productIds) {
        for (int productId : productIds) {
            CompletableFuture.runAsync(() -> getDescriptionService(productId));
        }
    }

    private void getDescriptionService(final int productId) {
        try {
            getDescription.apply(productId);
        } catch (UseCaseException ignored) {
        }
    }
}
