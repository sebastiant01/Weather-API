package com.api.corporationX.weather.app.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;

/**
 * Centralized exception handler for the Weather App REST API.
 *
 * <p>Intercepts exceptions thrown during request processing and returns structured
 * {@link ErrorDetails} responses with appropriate HTTP status codes, instead of
 * exposing raw stack traces to the client.</p>
 *
 * <p>Handled exceptions:</p>
 * <ul>
 *   <li>{@link HttpClientErrorException.NotFound} — city not found (HTTP 404)</li>
 *   <li>{@link HttpClientErrorException.Unauthorized} — invalid API key (HTTP 401)</li>
 *   <li>{@link Exception} — any other unexpected error (HTTP 500)</li>
 * </ul>
 */
public class GlobalExceptionHandler {

    /**
     * Handles the case where the requested city is not found by the Visual Crossing API.
     *
     * <p>Triggered when the external API responds with a {@code 404 Not Found} status,
     * typically because the city name provided in the URL path does not exist or
     * could not be resolved.</p>
     *
     * @param exception the {@link HttpClientErrorException.NotFound} thrown by {@code RestTemplate}
     * @return a {@link ResponseEntity} containing an {@link ErrorDetails} body with HTTP 404
     */
    @ExceptionHandler(HttpClientErrorException.NotFound.class)
    public ResponseEntity<ErrorDetails> handleCityNotFound(HttpClientErrorException.NotFound exception) {
        ErrorDetails messageError = new ErrorDetails("Error: City not found.",
                "The city's name doesn't exist or was not found.");
        return new ResponseEntity<>(messageError, HttpStatus.NOT_FOUND);
    }

    /**
     * Handles the case where the Visual Crossing API key is missing or invalid.
     *
     * <p>Triggered when the external API responds with a {@code 401 Unauthorized} status,
     * typically due to a misconfigured or expired API key in {@code application.properties}.</p>
     *
     * @param exception the {@link HttpClientErrorException.Unauthorized} thrown by {@code RestTemplate}
     * @return a {@link ResponseEntity} containing an {@link ErrorDetails} body with HTTP 401
     */
    @ExceptionHandler(HttpClientErrorException.Unauthorized.class)
    public ResponseEntity<ErrorDetails> handleUnauthorizedKey(HttpClientErrorException.Unauthorized exception) {
        ErrorDetails messageError = new ErrorDetails("Error: API Key is not valid.",
                "Check the API Key on configuration.");
        return new ResponseEntity<>(messageError, HttpStatus.UNAUTHORIZED);
    }

    /**
     * Handles any unexpected exception not covered by the more specific handlers above.
     *
     * <p>Acts as a catch-all fallback to prevent unhandled exceptions from propagating
     * to the client as raw error pages or stack traces. The exception's own message
     * is included in the response details for debugging purposes.</p>
     *
     * @param exception the generic {@link Exception} that was thrown
     * @return a {@link ResponseEntity} containing an {@link ErrorDetails} body with HTTP 500
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetails> handleGenericErrors(Exception exception) {
        ErrorDetails messageError = new ErrorDetails("Error: Occurred an internal error in server.",
                exception.getMessage());
        return new ResponseEntity<>(messageError, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}