package tech.between.interview.core.usecases.utils;

public class Errors {
    private Errors() {
    }
    public static final String MESSAGE_PRODUCT_DESCRIPTION_FAILED = "Failed call to product description with id '%s'";
    public static final String MESSAGE_PRODUCT_SIMILAR_FAILED = "Failed call to product similar ids with id '%s'";
    public static final String MESSAGE_PRODUCT_DESCRIPTION_EMPTY = "Description it's empty";
    public static final String CODE_PRODUCT_DESCRIPTION = "product_description";
    public static final String CODE_PRODUCT_SIMILAR = "product_similar";
}
