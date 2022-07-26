package tech.between.interview.repositories.exceptions;

import lombok.Getter;

public class RepositoryException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    @Getter
    private final int statusCode;
    @Getter
    private final String url;

    public RepositoryException(String message, int statusCode, String url, Throwable cause) {
        super(message, cause);
        this.statusCode = statusCode;
        this.url = url;
    }
}
