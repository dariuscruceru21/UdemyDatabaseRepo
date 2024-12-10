package Exceptions;

import java.sql.SQLException;

/**
 * Represents exceptions that occur during database operations.
 */
public class DataBaseException extends RuntimeException {
    public DataBaseException(String message) {
        super(message);
    }

    public DataBaseException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Factory method to handle specific SQL errors like foreign key violations.
     *
     * @param sqlException the SQLException thrown.
     * @param action       the database action being performed (e.g., "inserting", "updating").
     * @return a DataBaseException with a meaningful message.
     */
    public static DataBaseException handleSQLException(SQLException sqlException, String action) {
        String sqlState = sqlException.getSQLState();
        int errorCode = sqlException.getErrorCode();

        // Check for foreign key violation
        if ("23503".equals(sqlState) || errorCode == 1216 || errorCode == 1217) {
            return new DataBaseException("Foreign key violation occurred while " + action + ". Ensure all references exist.", sqlException);
        }

        // Fallback for general SQL exceptions
        return new DataBaseException("Database error occurred while " + action + ": " + sqlException.getMessage(), sqlException);
    }
}
