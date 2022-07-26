package tech.between.interview.core.usecases.products;

import tech.between.interview.core.entities.ProductDescription;

import java.util.List;
import java.util.function.Function;

public interface GetSimilarProducts extends Function<Integer, List<ProductDescription>> {
}
