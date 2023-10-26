package start.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import start.exception.exceptions.BadRequest;
import start.exception.exceptions.NotAllowException;

@ControllerAdvice
public class ApiHandleException {
    @ExceptionHandler(BadRequest.class)
    public ResponseEntity<?> duplicate(BadRequest exception){
        return new ResponseEntity<String>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
