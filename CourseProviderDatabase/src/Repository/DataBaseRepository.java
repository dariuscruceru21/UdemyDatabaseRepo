package Repository;

import Models.Identifiable;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.sql.*;
import java.util.Arrays;
import java.util.List;

public class DataBaseRepository<T extends Identifiable> implements IRepository<T> {
    private static final String URL = System.getenv("DB_URL");
    private static final String USER = System.getenv("DB_USER");
    private static final String PASSWORD = System.getenv("DB_PASSWORD");
    private final Class<T> type;
    private final String tableName;
    private final List<String> columnNames;
    private final Constructor<T> constructor;

    /**
     * Establishes a connection to the database.
     *
     * @return A Connection object.
     * @throws SQLException If a database access error occurs.
     */
    protected Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public DataBaseRepository(String tableName, Class<T> type, List<String> columnNames) {
        this.type = type;
        this.tableName = tableName;
        this.columnNames = columnNames;
        this.constructor = getConstructor();
    }

    private Constructor<T> getConstructor() {
        return Arrays.stream(type.getDeclaredConstructors())
                .filter(c -> c.getParameterCount() == columnNames.size()) // Match exact parameter count
                .map(c -> (Constructor<T>) c)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No suitable constructor found"));
    }

    @Override
    public void create(T obj) {
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = getConnection();
            StringBuilder sql = new StringBuilder("INSERT INTO " + tableName + " (");
            StringBuilder values = new StringBuilder(") VALUES (");

            for (int i = 0; i < columnNames.size(); i++) {
                sql.append(columnNames.get(i));
                values.append("?");
                if (i < columnNames.size() - 1) {
                    sql.append(", ");
                    values.append(", ");
                }
            }
            sql.append(values).append(")");

            stmt = conn.prepareStatement(sql.toString());
            for (int i = 0; i < columnNames.size(); i++) {
                String columnName = columnNames.get(i);

                // Find the field in the class hierarchy
                Field field = findField(type, columnName);
                if (field == null) {
                    throw new NoSuchFieldException(columnName + " not found in class " + type.getName());
                }

                field.setAccessible(true);
                Object value = field.get(obj);

                if (value instanceof List) {
                    Array sqlArray = conn.createArrayOf("text", ((List<?>) value).toArray());
                    stmt.setArray(i + 1, sqlArray);
                } else {
                    stmt.setObject(i + 1, value);
                }
            }

            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating object failed, no rows affected.");
            }

        } catch (SQLException | IllegalAccessException | NoSuchFieldException e) {
            e.printStackTrace();
        } finally {
            closeResources(conn, stmt, null);
        }
    }

    private Field findField(Class<?> clazz, String fieldName) {
        Class<?> current = clazz;
        while (current != null) {
            try {
                return current.getDeclaredField(fieldName);
            } catch (NoSuchFieldException e) {
                // Move to the superclass
                current = current.getSuperclass();
            }
        }
        return null; // Field not found
    }

    @Override
    public T get(Integer id) {
        return null; // Implementation placeholder
    }

    @Override
    public void update(Identifiable obj) {
        // Implementation placeholder
    }

    @Override
    public void delete(Integer id) {
        // Implementation placeholder
    }

    @Override
    public List getAll() {
        return List.of(); // Implementation placeholder
    }

    protected void closeResources(Connection conn, Statement stmt, ResultSet rs) {
        try {
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
            if (conn != null) conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
