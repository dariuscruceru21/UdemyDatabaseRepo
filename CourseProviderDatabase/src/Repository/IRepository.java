package Repository;

import Models.Identifiable;

import java.util.List;


public interface IRepository<T extends Identifiable> {
    /**
     * Adds a new entity to the repository.
     * @param obj The entity to add.
     * @return The added entity.
     */
    void create(T obj);


    /**
     * Retrieves an object from the repository by its ID.
     *
     * @param id The unique identifier of the object to retrieve.
     * @return The object with the specified ID, or null if not found.
     */
    T get(Integer id);


    /**
     * Updates an existing entity.
     * @param obj The entity with updated values.
     * @return The updated entity if the update was successful, or empty if not found.
     */
    void update(T obj);

    /**
     * Deletes an entity by its ID.
     * @param id The ID of the entity to delete.
     * @return True if the entity was deleted, false if not found.
     */
    void delete(Integer id);

    /**
     * Retrieves all objects from the repository.
     *
     * @return A list of all objects in the repository.
     */
    List<T> getAll();
}