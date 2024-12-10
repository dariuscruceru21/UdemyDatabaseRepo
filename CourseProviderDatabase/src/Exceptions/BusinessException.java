package Exceptions;

/**
 * Exception class that represents a business logic error.
 * This is a custom exception that can be used to signal specific
 * business rule violations in the application.
 */
public class BusinessException extends Exception {

    /**
     * Constructs a new BusinessException with the specified detail message.
     *
     * @param message the detail message (which is saved for later retrieval
     *                by the {@link Throwable#getMessage()} method).
     */
    public BusinessException(String message) {
        super(message);
    }
}
