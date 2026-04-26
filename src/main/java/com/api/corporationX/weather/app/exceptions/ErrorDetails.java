package com.api.corporationX.weather.app.exceptions;

import java.time.LocalDateTime;

/**
 * Represents the structured error response body returned to the client when an exception occurs.
 *
 * <p>This class is used by {@link GlobalExceptionHandler} to build consistent error payloads.
 * The timestamp is automatically set to the current date and time at the moment of instantiation.</p>
 *
 * <p>Example JSON response generated from this class:</p>
 * <pre>{@code
 * {
 *   "timestamp": "2025-04-25T13:00:00",
 *   "message": "Error: City not found.",
 *   "details": "The city's name doesn't exist or was not found."
 * }
 * }</pre>
 */
public class ErrorDetails {

    /** The date and time when the error occurred, set automatically on construction. */
    private LocalDateTime timestamp;

    /** A short, human-readable description of the error. */
    private String message;

    /** Additional context or technical details about the error. */
    private String details;

    /**
     * Constructs a new {@code ErrorDetails} instance with the given message and details.
     * The {@code timestamp} field is automatically initialized to the current date and time.
     *
     * @param message a short description of the error (e.g., {@code "Error: City not found."})
     * @param details additional context about the error (e.g., the cause or a suggested fix)
     */
    public ErrorDetails(String message, String details) {
        this.message = message;
        this.details = details;
        this.timestamp = LocalDateTime.now();
    }

    /**
     * Returns the timestamp indicating when the error occurred.
     *
     * @return the error timestamp as a {@link LocalDateTime}
     */
    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    /**
     * Sets the error timestamp.
     *
     * @param timestamp the timestamp to set
     */
    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    /**
     * Returns the short error message.
     *
     * @return the error message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Sets the short error message.
     *
     * @param message the message to set
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Returns the detailed description of the error.
     *
     * @return the error details
     */
    public String getDetails() {
        return details;
    }

    /**
     * Sets the detailed description of the error.
     *
     * @param details the details to set
     */
    public void setDetails(String details) {
        this.details = details;
    }
}