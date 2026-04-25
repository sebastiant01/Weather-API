package com.api.corporationX.weather.app.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;

public class GlobalExceptionHandler {

    @ExceptionHandler(HttpClientErrorException.NotFound.class)
    public ResponseEntity<ErrorDetails> handleCityNotFound(HttpClientErrorException.NotFound exception) {
        ErrorDetails messageError = new ErrorDetails("Error: City not found.",
                "The city's code doesn't exist or was not found.");
        return new ResponseEntity<>(messageError, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(HttpClientErrorException.Unauthorized.class)
    public ResponseEntity<ErrorDetails> handleUnauthorizedKey(HttpClientErrorException.Unauthorized exception) {
        ErrorDetails messageError = new ErrorDetails("Error: API Key is not valid.",
                "Check the API Key on configuration.");
        return new ResponseEntity<>(messageError, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetails> handleGenericErrors(Exception exception) {
        ErrorDetails messageError = new ErrorDetails("Error: Occurred an internal error in server.",
                exception.getMessage());
        return new ResponseEntity<>(messageError, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
