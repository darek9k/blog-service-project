package darek9k.common;

import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

@ControllerAdvice
public class ErrorHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationEx(MethodArgumentNotValidException ex){
        ex.getBindingResult().getFieldErrors().forEach(fieldError -> System.out.println(fieldError.getField() + ":" + fieldError.getDefaultMessage()));

        Map<String, String> fieldsErrorMap = new HashMap<>();
        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            if (fieldsErrorMap.put(fieldError.getField(), fieldError.getDefaultMessage()) != null) {
                throw new IllegalStateException("Duplicate key");
            }
        }
        return ResponseEntity.badRequest().body(fieldsErrorMap);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<String> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex){
        System.out.println(ex.getMessage());
        return ResponseEntity.badRequest().body("Invalid JSON");
    }
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<String> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex){
        return ResponseEntity.badRequest().build();
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<String> handleNoSuchElementException(NoSuchElementException ex){
        return ResponseEntity.notFound().build();
    }
}
