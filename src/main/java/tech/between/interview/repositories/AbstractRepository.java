package tech.between.interview.repositories;

import io.netty.util.internal.StringUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpStatusCodeException;
import tech.between.interview.repositories.exceptions.RepositoryException;
import tech.between.interview.repositories.exceptions.http.*;

@Log4j2
public abstract class AbstractRepository {


    protected void launchHttpStatusCodeException(final HttpStatusCodeException exception, final String url) {
        final HttpStatus status = exception.getStatusCode();

        log.debug("error url: " + url, exception);
        if (HttpStatus.BAD_REQUEST.value() == status.value()) {
            throw new BadRequestException(url, exception);
        } else if (HttpStatus.NOT_FOUND.value() == status.value()) {
            throw new NotFoundException(url, exception);
        } else if (HttpStatus.CONFLICT.value() == status.value()) {
            throw new ConflictException(url, exception);
        } else if (HttpStatus.TOO_MANY_REQUESTS.value() == status.value()) {
            throw new TooManyRequestsException(url, exception);
        } else if (HttpStatus.INTERNAL_SERVER_ERROR.value() == status.value()) {
            throw new InternalServerErrorException(url, exception);
        } else {
            throw new RepositoryException(String.format("Status code %s unknown", status.value()), status.value(),
                    url, exception);
        }
    }

    protected void launchRepositoryException(final Exception e, final String message) {
        throw new RepositoryException(message,
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                StringUtil.EMPTY_STRING, e);
    }
}
