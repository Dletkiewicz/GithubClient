package pl.dariusz.github.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<UserNotFoundObject> handleUserNotFoundException(UserNotFoundException e, WebRequest request){
        UserNotFoundObject errorObject = new UserNotFoundObject();

        errorObject.setStatusCode(HttpStatus.NOT_FOUND.value());
        errorObject.setMessage(e.getMessage());
        return new ResponseEntity<UserNotFoundObject>(errorObject, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UnsupportedHeaderException.class)
    public ResponseEntity<UnsupportedHeaderObjectError> handleUnsupportedMediaTypeException(UnsupportedHeaderException e, WebRequest request) {
        UnsupportedHeaderObjectError errorResponse = new UnsupportedHeaderObjectError();

        errorResponse.setStatusCode(HttpStatus.NOT_ACCEPTABLE.value());
        errorResponse.setMessage(e.getMessage());

        return new ResponseEntity<UnsupportedHeaderObjectError>(errorResponse, HttpStatus.NOT_ACCEPTABLE);
    }
}
