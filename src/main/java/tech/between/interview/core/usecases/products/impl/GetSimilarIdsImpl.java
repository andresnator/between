package tech.between.interview.core.usecases.products.impl;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.between.interview.core.repositories.GetProductSimilarIdsRepository;
import tech.between.interview.core.usecases.exceptions.UseCaseException;
import tech.between.interview.core.usecases.products.GetSimilarIds;
import tech.between.interview.repositories.exceptions.RepositoryException;

import java.util.Set;

import static java.lang.String.format;
import static tech.between.interview.core.usecases.utils.Errors.CODE_PRODUCT_SIMILAR;
import static tech.between.interview.core.usecases.utils.Errors.MESSAGE_PRODUCT_SIMILAR_FAILED;

@Service
public class GetSimilarIdsImpl implements GetSimilarIds {

    private final GetProductSimilarIdsRepository similarIdsRepository;

    @Autowired
    public GetSimilarIdsImpl(GetProductSimilarIdsRepository productRepository) {
        this.similarIdsRepository = productRepository;
    }

    @Override
    public Set<Integer> apply(@NonNull Integer productId) {
        final Set<Integer> similarIds;
        try {
            similarIds = this.similarIdsRepository.getSimilarIds(productId);
        } catch (RepositoryException e) {
            final String message = format(MESSAGE_PRODUCT_SIMILAR_FAILED, productId);
            throw new UseCaseException(CODE_PRODUCT_SIMILAR, message, e.getStatusCode(), e);
        }
        return similarIds;
    }
}
