package dev.buddly.flow_sync.exception;

import dev.buddly.flow_sync.dto.ResponseHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Object> handleNotFoundException(NotFoundException exception){
        log.error("Not found exception occurred: {}", exception.getMessage());
        //eklenen
        return ResponseHandler.responseBuilder(exception.getMessage(),HttpStatus.NOT_FOUND,null);
    }

    @ExceptionHandler(AlreadyExistsException.class)
    public ResponseEntity<Object> handleAlreadyExistsException(AlreadyExistsException exception){
        log.error("Already exists exception occurred: {}", exception.getMessage());
        return ResponseHandler.responseBuilder(exception.getMessage(),HttpStatus.BAD_REQUEST,null);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationExceptions(MethodArgumentNotValidException ex) {
        StringBuilder errorMessage = new StringBuilder();
        ex.getBindingResult().getFieldErrors().forEach((FieldError error) -> {
            errorMessage.append(error.getDefaultMessage()).append("; ");
        });
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(errorMessage.toString());
    }
}
