package project1.constant.exception;


import io.jsonwebtoken.JwtException;
import project1.constant.response.JsonResponse;
import project1.constant.response.JsonResponseStatus;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class BaseExceptionHandler {

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(BaseException.class)
    public JsonResponse<JsonResponseStatus> BaseExceptionHandle(BaseException exception) {
        return new JsonResponse<>(JsonResponseStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(DuplicateException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public JsonResponse<JsonResponseStatus> DuplicateExceptionHandle(DuplicateException exception) {
        return new JsonResponse<>(exception.getStatus());
    }

    @ExceptionHandler(JwtException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public JsonResponse<JsonResponseStatus> JwtExceptionHandle(JwtException exception) {
        return new JsonResponse<>(JsonResponseStatus.TOKEN_EXPIRED);
    }
}
