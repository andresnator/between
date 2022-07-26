package tech.between.interview.repositories.exceptions.http;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import tech.between.interview.repositories.exceptions.RepositoryException;

@ResponseStatus(HttpStatus.CONFLICT)
public class ConflictException extends RepositoryException {
    private static final HttpStatus HTTP_STATUS = HttpStatus.CONFLICT;

    public ConflictException(String url, Throwable cause) {
        super(HTTP_STATUS.getReasonPhrase(), HTTP_STATUS.value(), url, cause);
    }
}
