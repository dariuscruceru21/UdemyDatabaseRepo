package Exceptions;

import java.util.regex.Pattern;

public class ValidationException extends Exception{
    public ValidationException(String message){
        super(message);
    }

    /**
     * Validates if the given email matches the required format (__@__.__).
     *
     * @param email the email to validate.
     * @throws ValidationException if the email is invalid.
     */
    public static void validateEmail(String email) throws ValidationException {
        String emailRegex = "^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$"; // Regex for email validation
        Pattern pattern = Pattern.compile(emailRegex);

        if (!pattern.matcher(email).matches()) {
            throw new ValidationException("Invalid email format. Expected format is __@__.__");
        }
    }

    /**
     * Validates if the given ID is greater than 0.
     *
     * @param id the ID to validate.
     * @throws ValidationException if the ID is not greater than 0.
     */
    public static void validateId(int id) throws ValidationException {
        if (id <= 0) {
            throw new ValidationException("Invalid ID. ID must be greater than 0.");
        }
    }
}
