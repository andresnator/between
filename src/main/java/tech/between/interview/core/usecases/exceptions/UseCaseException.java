package tech.between.interview.core.usecases.exceptions;

import lombok.Getter;

public class UseCaseException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    @Getter
    private final String code;
    @Getter
    private final String message;
    @Getter
    private final Integer statusCode;

    public UseCaseException(final String code, final String message) {
        super(message);
        this.code = code;
        this.message = message;
        this.statusCode = null;
    }

    public UseCaseException(final String code, final String message, final Integer statusCode, final Throwable cause) {
        super(message, cause);
        this.code = code;
        this.message = message;
        this.statusCode = statusCode;
    }

}
