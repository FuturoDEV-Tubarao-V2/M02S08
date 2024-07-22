package br.com.futurodev.tasksapirest.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.Objects;

@RestControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<ProblemDetail> handleMethodArgumentNotValidException(MethodArgumentNotValidException e,
                                                                                  HttpServletRequest request) {
        BindingResult bindingResult = e.getBindingResult();

        List<String> result = bindingResult.getFieldErrors()
                .stream()
                .map(error -> Objects.requireNonNull(error.getDefaultMessage()).replace("{}", error.getField()))
                .toList();

        String detail = String.join("\n", result);
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, detail);
        problemDetail.setTitle("Erro ao validar campos do formulário");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(problemDetail);
    }

    @ExceptionHandler(PersonNotFoundException.class)
    protected ResponseEntity<ProblemDetail> handlePersonNotFoundException(PersonNotFoundException e,
                                                                          HttpServletRequest request) {
        String detail = e.getMessage();
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, detail);
        problemDetail.setTitle(e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(problemDetail);
    }

    @ExceptionHandler(TaskNotFoundException.class)
    protected ResponseEntity<ProblemDetail> handleTaskNotFoundException(TaskNotFoundException e,
                                                                        HttpServletRequest request) {
        String detail = e.getMessage();
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, detail);
        problemDetail.setTitle(e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(problemDetail);
    }
}
