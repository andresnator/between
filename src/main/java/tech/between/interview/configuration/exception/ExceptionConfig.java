package tech.between.interview.configuration.exception;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import tech.between.interview.configuration.exception.dto.ApiError;
import tech.between.interview.core.usecases.exceptions.UseCaseException;
import tech.between.interview.repositories.exceptions.RepositoryException;

import java.util.Objects;

import static java.lang.String.format;

@Log4j2
@ControllerAdvice
public class ExceptionConfig {

    private static final String PARAMETER_VALUE_REQUIRED = "Parameter [%s] value type it's wrong";

    @ExceptionHandler(value = {UseCaseException.class})
    protected ResponseEntity<ApiError> handleUseCaseException(UseCaseException e) {
        ApiError apiError;
        if (Objects.nonNull(e.getStatusCode())) {
            final Integer statusCode = e.getStatusCode();
            log.warn(format("Internal Api error. Status Code: %s", statusCode), e);
            apiError = new ApiError(e.getCode(), e.getMessage(), statusCode);
        } else {
            apiError = new ApiError(e.getCode(), e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value());
        }

        log.error("Internal Api error", e);
        return ResponseEntity.status(apiError.getStatus()).body(apiError);
    }

    @ExceptionHandler(value = {RepositoryException.class})
    protected ResponseEntity<ApiError> handleRepositoryException(RepositoryException e) {
        log.error("RepositoryException error", e);
        String message = String.format("%s %s", e.getMessage(), e.getUrl());
        ApiError apiError = new ApiError("repository_error", message, e.getStatusCode());
        return ResponseEntity.status(apiError.getStatus()).body(apiError);
    }

    @ExceptionHandler(value = {MethodArgumentTypeMismatchException.class})
    protected ResponseEntity<ApiError> handleUnknownException(MethodArgumentTypeMismatchException e) {
        final String message = format(PARAMETER_VALUE_REQUIRED, e.getParameter());
        final ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST.getReasonPhrase(), message, HttpStatus.BAD_REQUEST.value());
        return ResponseEntity.status(apiError.getStatus()).body(apiError);
    }

    @ExceptionHandler(value = {Exception.class})
    protected ResponseEntity<ApiError> handleUnknownException(Exception e) {
        log.error("Internal error", e);
        ApiError apiError = new ApiError("internal_error", "Internal server error", HttpStatus.INTERNAL_SERVER_ERROR.value());
        return ResponseEntity.status(apiError.getStatus()).body(apiError);
    }

}