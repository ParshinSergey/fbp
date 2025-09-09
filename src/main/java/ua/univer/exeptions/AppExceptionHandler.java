package ua.univer.exeptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.net.SocketTimeoutException;
import java.time.format.DateTimeParseException;

@ControllerAdvice
@Slf4j
public class AppExceptionHandler {

    public final static String TEXT_MISTAKE = "{\"textmistake\": \"%s\"}";

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleValidationExceptions (MethodArgumentNotValidException ex) {
        StringBuilder sb = new StringBuilder();
        ex.getBindingResult().getFieldErrors().forEach(fe -> sb.append(fe.getField()).append(" ").append(fe.getDefaultMessage()).append("; "));
        log.warn(sb.toString());
        return new ResponseEntity<>(String.format(TEXT_MISTAKE, sb.toString()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DateTimeParseException.class)
    public ResponseEntity<String> handleDateTimeExceptions (DateTimeParseException ex) {
        String answer = String.format(TEXT_MISTAKE, "Ошибка в дате: " + ex.getMessage());
        log.warn(answer);
        return ResponseEntity.badRequest().body(answer);
    }

    @ExceptionHandler(MyException.class)
    public ResponseEntity<String> handleMyExceptions (MyException ex) {
        String answer = String.format(TEXT_MISTAKE, ex.getMessage());
        log.warn(answer);
        return ResponseEntity.internalServerError().body(answer);
    }

    @ExceptionHandler(UnprocessableEntityException.class)
    public ResponseEntity<String> handleUnprocessableEntityException (UnprocessableEntityException ex) {
        String answer = String.format(TEXT_MISTAKE, ex.getMessage());
        log.warn(answer);
        return ResponseEntity.unprocessableEntity().body(answer);
    }

    @ExceptionHandler(SocketTimeoutException.class)
    public ResponseEntity<String> handleSocketTimeout (SocketTimeoutException ex) {
        String answer = String.format(TEXT_MISTAKE, ex.getMessage());
        log.warn(answer);
        return ResponseEntity.internalServerError().body(answer);
    }



}
