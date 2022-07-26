package tech.between.interview.repositories.exceptions.http;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import tech.between.interview.repositories.exceptions.RepositoryException;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class InternalServerErrorException extends RepositoryException {
    private static final HttpStatus HTTP_STATUS = HttpStatus.INTERNAL_SERVER_ERROR;

    public InternalServerErrorException(String url, Throwable cause) {
        super(HTTP_STATUS.getReasonPhrase(), HTTP_STATUS.value(), url, cause);
    }
}
