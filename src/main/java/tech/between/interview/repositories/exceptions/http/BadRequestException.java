package tech.between.interview.repositories.exceptions.http;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import tech.between.interview.repositories.exceptions.RepositoryException;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class BadRequestException extends RepositoryException {
    private static final HttpStatus HTTP_STATUS = HttpStatus.BAD_REQUEST;

    public BadRequestException(String url, Throwable cause) {
        super(HTTP_STATUS.getReasonPhrase(), HTTP_STATUS.value(), url, cause);
    }
}
