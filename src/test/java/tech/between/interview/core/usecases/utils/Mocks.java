package tech.between.interview.core.usecases.utils;

import tech.between.interview.core.entities.ProductDescription;

public class Mocks {
    public static ProductDescription productDescription(){
        ProductDescription product =new ProductDescription();
        product.setId("1");
        return product;
    }
}
