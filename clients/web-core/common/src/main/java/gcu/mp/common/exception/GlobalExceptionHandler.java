package gcu.mp.common.exception;


import gcu.mp.common.api.BaseResponseStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(BaseException.class)
    protected ResponseEntity<BaseException> handleCustomException(final BaseException e) {
        log.error("handleCustomException: {}", e.getStatus().getCode());
        return ResponseEntity
                .status(e.getStatus().getHttpCode())
                .body(new BaseException(e.getStatus()));
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    protected ResponseEntity<BaseException> handleHttpRequestMethodNotSupportedException(final HttpRequestMethodNotSupportedException e) {
        log.error("handleHttpRequestMethodNotSupportedException: {}", e.getMessage());
        return ResponseEntity
                .status(HttpStatus.METHOD_NOT_ALLOWED)
                .body(new BaseException(BaseResponseStatus.METHOD_NOT_ALLOWED));
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<BaseException> handleException(final Exception e) {
        log.error("handleException: {}", e.getMessage());
        log.error(String.valueOf(e.getCause()));
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new BaseException(BaseResponseStatus.SERVER_ERROR));
    }

}