package tech.between.interview.repositories.exceptions.http;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import tech.between.interview.repositories.exceptions.RepositoryException;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundException extends RepositoryException {
    private static final HttpStatus HTTP_STATUS = HttpStatus.NOT_FOUND;

    public NotFoundException(String url, Throwable cause) {
        super(HTTP_STATUS.getReasonPhrase(), HTTP_STATUS.value(), url, cause);
    }
}
