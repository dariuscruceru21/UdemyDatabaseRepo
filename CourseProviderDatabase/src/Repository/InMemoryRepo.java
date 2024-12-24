package Repository;

import Models.Identifiable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryRepo<T extends Identifiable>implements IRepository<T> {

    private Map<Integer, T> entities = new HashMap<>();
    @Override
    public void create(T obj) {
        if (obj != null && obj.getId() != null) {
            entities.put(obj.getId(), obj);
        }
    }

    @Override
    public T get(Integer id) {
        return entities.get(id);
    }

    @Override
    public void update(T obj) {
        if (obj != null && obj.getId() != null) {
            entities.put(obj.getId(), obj);
        }
    }

    @Override
    public void delete(Integer id) {
        entities.remove(id);
    }

    @Override
    public List<T> getAll() {
        return new ArrayList<>(entities.values());
    }
}
