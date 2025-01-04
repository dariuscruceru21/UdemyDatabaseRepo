package Repository;

import Models.Identifiable;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvException;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class FileRepoistory<T extends Identifiable> implements IRepository<T> {
    private final String fileName;
    private final Class<T> entityClass;

    public FileRepoistory(String fileName, Class<T> entityClass) {
        this.fileName = fileName;
        this.entityClass = entityClass;
    }

    @Override
    public T get(Integer id) {
        return getAll().stream()
                .filter(entity -> entity.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public void delete(Integer id) {
        List<T> entities = getAll();
        entities.removeIf(entity -> entity.getId().equals(id));
        saveAll(entities);
    }

    @Override
    public void update(T entity) {
        List<T> entities = getAll();
        for (int i = 0; i < entities.size(); i++) {
            if (entities.get(i).getId().equals(entity.getId())) {
                entities.set(i, entity);
                break;
            }
        }
        saveAll(entities);
    }

    @Override
    public List<T> getAll() {
        List<T> entities = new ArrayList<>();
        try (CSVReader reader = new CSVReader(new FileReader(fileName))) {
            List<String[]> rows = reader.readAll();
            if (!rows.isEmpty()) {
                for (String[] row : rows) {
                    T entity = createEntityFromCsv(row);
                    if (entity != null) {
                        entities.add(entity);
                    }
                }
            }
        } catch (IOException | CsvException e) {
            e.printStackTrace();
        }
        return entities;
    }

    @Override
    public void create(T entity) {
        List<T> entities = getAll();
        entities.add(entity);
        saveAll(entities);
    }

    private void saveAll(List<T> entities) {
        try (CSVWriter writer = new CSVWriter(new FileWriter(fileName))) {
            for (T entity : entities) {
                writer.writeNext(convertEntityToCsv(entity));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private T createEntityFromCsv(String[] data) {
        try {
            Constructor<?>[] constructors = entityClass.getDeclaredConstructors();
            Constructor<?> constructor = Arrays.stream(constructors)
                    .max((c1, c2) -> c1.getParameterCount() - c2.getParameterCount())
                    .orElseThrow(() -> new RuntimeException("No suitable constructor found"));

            constructor.setAccessible(true);
            Object[] params = new Object[constructor.getParameterCount()];
            Class<?>[] parameterTypes = constructor.getParameterTypes();

            // Only process up to the minimum of data length and parameter count
            int minLength = Math.min(data.length, constructor.getParameterCount());

            for (int i = 0; i < minLength; i++) {
                params[i] = convertToType(data[i], parameterTypes[i]);
            }

            // Fill remaining parameters with null or default values
            for (int i = minLength; i < constructor.getParameterCount(); i++) {
                params[i] = getDefaultValue(parameterTypes[i]);
            }

            return (T) constructor.newInstance(params);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            return null;
        }
    }

    private Object convertToType(String value, Class<?> type) {
        if (value == null || value.trim().isEmpty()) {
            return getDefaultValue(type);
        }

        try {
            if (type == Integer.class || type == int.class) {
                return Integer.parseInt(value.trim());
            } else if (type == Long.class || type == long.class) {
                return Long.parseLong(value.trim());
            } else if (type == Double.class || type == double.class) {
                return Double.parseDouble(value.trim());
            } else if (type == Boolean.class || type == boolean.class) {
                return Boolean.parseBoolean(value.trim());
            } else if (type == List.class) {
                return Arrays.asList(value.split(","));
            }
            return value;
        } catch (NumberFormatException e) {
            return getDefaultValue(type);
        }
    }

    private Object getDefaultValue(Class<?> type) {
        if (type == int.class) return 0;
        if (type == long.class) return 0L;
        if (type == double.class) return 0.0;
        if (type == boolean.class) return false;
        if (type == List.class) return new ArrayList<>();
        return null;
    }

    private String[] convertEntityToCsv(T entity) {
        List<String> values = new ArrayList<>();
        Field[] fields = entityClass.getDeclaredFields();

        for (Field field : fields) {
            field.setAccessible(true);
            try {
                Object value = field.get(entity);
                if (value instanceof List) {
                    values.add(((List<?>) value).stream()
                            .map(Object::toString)
                            .collect(Collectors.joining(",")));
                } else {
                    values.add(value != null ? value.toString() : "");
                }
            } catch (IllegalAccessException e) {
                values.add("");
            }
        }
        return values.toArray(new String[0]);
    }
}