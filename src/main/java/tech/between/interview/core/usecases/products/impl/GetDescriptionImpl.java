package tech.between.interview.core.usecases.products.impl;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import tech.between.interview.core.entities.ProductDescription;
import tech.between.interview.core.repositories.GetProductDescriptionRepository;
import tech.between.interview.core.usecases.exceptions.UseCaseException;
import tech.between.interview.core.usecases.products.GetDescription;
import tech.between.interview.repositories.exceptions.RepositoryException;

import static java.lang.String.format;
import static tech.between.interview.configuration.cache.CacheConfig.DESCRIPTION_PRODUCT;
import static tech.between.interview.core.usecases.utils.Errors.*;

@Service
public class GetDescriptionImpl implements GetDescription {

    private final GetProductDescriptionRepository descriptionRepository;

    @Autowired
    public GetDescriptionImpl(GetProductDescriptionRepository descriptionRepository) {
        this.descriptionRepository = descriptionRepository;
    }

    @Override
    @Cacheable(DESCRIPTION_PRODUCT)
    public ProductDescription apply(@NonNull Integer productId) {
        ProductDescription description;
        try {
            description = this.descriptionRepository.getDescription(productId)
                    .orElseThrow(() -> new UseCaseException(CODE_PRODUCT_DESCRIPTION, MESSAGE_PRODUCT_DESCRIPTION_EMPTY));
        } catch (RepositoryException e) {
            final String message = format(MESSAGE_PRODUCT_DESCRIPTION_FAILED, productId);
            throw new UseCaseException(CODE_PRODUCT_DESCRIPTION, message, e.getStatusCode(), e);
        }

        return description;
    }
}

