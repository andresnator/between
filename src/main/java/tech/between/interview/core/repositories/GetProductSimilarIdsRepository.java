package tech.between.interview.core.repositories;

import java.util.Set;

@FunctionalInterface
public interface GetProductSimilarIdsRepository {
    Set<Integer> getSimilarIds(int productId);
}
