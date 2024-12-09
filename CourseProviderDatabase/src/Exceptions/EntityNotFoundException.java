package Exceptions;

public class EntityNotFoundException extends Exception {
    private final Integer entityId;
    public EntityNotFoundException(Integer entityId) {
        super("Entity with ID " + entityId + " not found.");
        this.entityId = entityId;
    }
    public Integer getEntityId() {
        return entityId;
    }

}
