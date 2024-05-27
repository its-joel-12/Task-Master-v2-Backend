package com.joel.task_master.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class TaskMasterExceptionHandler {

    @ExceptionHandler(value = {
            NumberFormatException.class,
            Exception.class,
            MethodArgumentTypeMismatchException.class
    })
    public ResponseEntity<Object> handleExceptions(Exception ex) {
        // RESOURCE NOT FOUND EXCEPTION
        if (ex instanceof ResourceNotFoundException) {
            TaskMasterException error = new TaskMasterException(
                    404,
                    HttpStatus.NOT_FOUND,
                    ex.getMessage(),
                    "Required Resource Not Found, hence the operation was unsuccessful!!"
            );
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
        // HttpMessageNotReadableException
        else if (ex instanceof HttpMessageNotReadableException) {
            TaskMasterException error = new TaskMasterException(
                    400,
                    HttpStatus.BAD_REQUEST,
                    "Please make sure you send data in proper format, also don't send an empty request body!!",
                    ex.getMessage()
                    );
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }
        // NoResourceFoundException
        else if (ex instanceof NoResourceFoundException) {
            TaskMasterException error = new TaskMasterException(
                    400,
                    HttpStatus.BAD_REQUEST,
                    "Check proper format of the api and its Http method!!",
                    ex.getMessage()
                    );
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }
        // IN VALID FIELD DATA
        if (ex instanceof MethodArgumentNotValidException) {
            Map<String, String> res = new HashMap<>();

            ((MethodArgumentNotValidException) ex).getBindingResult().getAllErrors().forEach((er) -> {
                String fieldName = ((FieldError) er).getField();
                String message = er.getDefaultMessage();
                res.put(fieldName, message);
            });
            TaskMasterException error = new TaskMasterException(
                    400,
                    HttpStatus.BAD_REQUEST,
                    "Invalid Inputs !!",
                    res.toString()
            );
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }
        // DataIntegrityViolationException
        else if (ex instanceof DataIntegrityViolationException) {
            TaskMasterException error = new TaskMasterException(
                    400,
                    HttpStatus.BAD_REQUEST,
                    "User already exists with the entered email !!",
                    ex.getMessage()
            );
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }
        // MethodArgumentTypeMismatchException
        else if (ex instanceof MethodArgumentTypeMismatchException) {
            TaskMasterException error = new TaskMasterException(
                    400,
                    HttpStatus.BAD_REQUEST,
                    "Please provide proper Integer ID input in the api url argument!!",
                    ex.getMessage()
                    );
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }
        // UN IDENTIFIED EXCEPTION
        else {
            TaskMasterException error = new TaskMasterException(
                    500,
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "Un identified error | Please contact the Backend Developer!!... :D",
                    ex.getMessage()
                    );
            return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
