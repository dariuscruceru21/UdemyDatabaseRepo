package Exceptions;

/**
 * Exception class that represents an error when an entity is not found.
 * This custom exception is thrown when an entity with a specific ID cannot
 * be found in the system.
 */
public class EntityNotFoundException extends Exception {
    // The ID of the entity that was not found
    private final Integer entityId;

    /**
     * Constructs a new EntityNotFoundException with a detail message that
     * includes the entity ID.
     *
     * @param entityId the ID of the entity that was not found
     */
    public EntityNotFoundException(Integer entityId) {
        super("Entity with ID " + entityId + " not found.");
        this.entityId = entityId;
    }

    /**
     * Returns the ID of the entity that was not found.
     *
     * @return the entity ID
     */
    public Integer getEntityId() {
        return entityId;
    }

}
