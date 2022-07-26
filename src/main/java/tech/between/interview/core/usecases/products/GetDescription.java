package tech.between.interview.core.usecases.products;

import tech.between.interview.core.entities.ProductDescription;

import java.util.function.Function;

public interface GetDescription extends Function<Integer, ProductDescription> {
}
