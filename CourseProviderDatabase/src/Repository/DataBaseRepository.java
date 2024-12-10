package Repository;

import Exceptions.DataBaseException;
import Models.Identifiable;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
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
                String fieldName = toCamelCase(columnName);

                Field field = findField(type, fieldName);
                if (field == null) {
                    throw new NoSuchFieldException(fieldName + " not found in class " + type.getName());
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
            throw DataBaseException.handleSQLException((SQLException) e, "inserting into " + tableName);
        } finally {
            closeResources(conn, stmt, null);
        }
    }


    // de vazut ce sa facem cu numele la tabel la idcolumname
    @Override
    public T get(Integer id) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        T result = null;

        try{
            connection = getConnection();
            // Determine the id column name based on the table name
            String idColumnName;
            if (tableName.equalsIgnoreCase("admin") ||
                    tableName.equalsIgnoreCase("instructor") ||
                    tableName.equalsIgnoreCase("student")) {
                idColumnName = "userid"; // Use "userid" for these specific tables
            } else if (tableName.equalsIgnoreCase("assignmentquiz")) {
                idColumnName = "assignmentid";
            } else if (tableName.equalsIgnoreCase("coursemodule")) {
                idColumnName = "moduleid";
            } else if (tableName.equalsIgnoreCase("messageforum")) {
                idColumnName = "messageid";
            } else if (tableName.equalsIgnoreCase("moduleassignment")) {
                idColumnName = "moduleid";
            } else if (tableName.equalsIgnoreCase("studentcourse")) {
                idColumnName = "studentid";
            } else {
                idColumnName = tableName.toLowerCase() + "id"; // Default logic
            }
            String sql = "SELECT * FROM " + tableName + " WHERE " + idColumnName + " = ?";
            statement = connection.prepareStatement(sql);
            statement.setInt(1,id);

            rs = statement.executeQuery();

            if(rs.next()){
                Object[] args = new Object[columnNames.size()];
                for(int i = 0 ; i < columnNames.size();i++){
                    String columnName = columnNames.get(i);
                    if(columnName.equals(idColumnName))
                        args[i] = rs.getInt(columnName);
                    else
                        args[i] = rs.getObject(columnName);
                }
                result = constructor.newInstance(args);

                result.setId(rs.getInt(idColumnName));
            }

        } catch (Exception e) {
            e.printStackTrace();
            DataBaseException.handleSQLException((SQLException) e, "getting from " + tableName);
        }finally {
            closeResources(connection,statement, rs);
        }
        return result;
    }

    @Override
    public void update(T obj) {
        Connection connection = null;
        PreparedStatement statement = null;
        try{
            connection = getConnection();
            StringBuilder sql = new StringBuilder("UPDATE " + tableName + " SET ");
            String idColumnName;
            if (tableName.equalsIgnoreCase("admin") ||
                    tableName.equalsIgnoreCase("instructor") ||
                    tableName.equalsIgnoreCase("student")) {
                idColumnName = "userid"; // Use "userid" for these specific tables
            } else if (tableName.equalsIgnoreCase("assignmentquiz")) {
                idColumnName = "assignmentid";
            } else if (tableName.equalsIgnoreCase("coursemodule")) {
                idColumnName = "moduleid";
            } else if (tableName.equalsIgnoreCase("messageforum")) {
                idColumnName = "messageid";
            } else if (tableName.equalsIgnoreCase("moduleassignment")) {
                idColumnName = "moduleid";
            } else if (tableName.equalsIgnoreCase("studentcourse")) {
                idColumnName = "studentid";
            } else {
                idColumnName = tableName.toLowerCase() + "id"; // Default logic
            }

            for(int i = 0; i < columnNames.size(); i++){
                if(!columnNames.get(i).equals(idColumnName)){
                    sql.append(columnNames.get(i)).append(" = ?");
                    if(i < columnNames.size() - 1){
                        sql.append(", ");
                    }
                }
            }
            sql.append(" WHERE ").append(idColumnName).append(" = ?");

            statement = connection.prepareStatement(sql.toString());
            int parameterIndex = 1;
            for(String columnnName : columnNames){
                if(!columnnName.equals(idColumnName)){
                    String fieldName = toCamelCase(columnnName);
                    Field field = findField(type,fieldName);
                    if(field == null){
                        throw new NoSuchFieldException(fieldName + " not found in class " + type.getName());
                    }

                    field.setAccessible(true);
                    Object value = field.get(obj);

                    if(value instanceof List) {
                        Array sqlArray = connection.createArrayOf("text",((List<?>) value).toArray());
                        statement.setArray(parameterIndex++, sqlArray);
                    }else {
                        statement.setObject(parameterIndex++, value);
                    }
                }
            }
            statement.setInt(parameterIndex, obj.getId());

            int affectedRows = statement.executeUpdate();
            if(affectedRows == 0){
                throw new SQLException("Updating object failed, no rows affected.");

            }
        } catch (SQLException | IllegalAccessException | NoSuchFieldException e) {
            e.printStackTrace();
            DataBaseException.handleSQLException((SQLException) e, "updating into " + tableName);
        }finally {
            closeResources(connection,statement,null);
        }
    }

    @Override
    public void delete(Integer id) {
        Connection connection = null;
        PreparedStatement statement = null;
        try{
            connection = getConnection();
            String idColumnName;
            if (tableName.equalsIgnoreCase("admin") ||
                    tableName.equalsIgnoreCase("instructor") ||
                    tableName.equalsIgnoreCase("student")) {
                idColumnName = "userid"; // Use "userid" for these specific tables
            } else if (tableName.equalsIgnoreCase("assignmentquiz")) {
                idColumnName = "assignmentid";
            } else if (tableName.equalsIgnoreCase("coursemodule")) {
                idColumnName = "moduleid";
            } else if (tableName.equalsIgnoreCase("messageforum")) {
                idColumnName = "messageid";
            } else if (tableName.equalsIgnoreCase("moduleassignment")) {
                idColumnName = "moduleid";
            } else if (tableName.equalsIgnoreCase("studentcourse")) {
                idColumnName = "studentid";
            } else {
                idColumnName = tableName.toLowerCase() + "id";// Default logic
            }
            String sql = "DELETE FROM " + tableName.toLowerCase() + " WHERE " + idColumnName + " = ?";

            statement = connection.prepareStatement(sql);
            statement.setInt(1,id);

            int affectedRows = statement.executeUpdate();
            if(affectedRows == 0){
                throw new SQLException("Deleeting object failed, no rows affected. ID may not exist");

            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw DataBaseException.handleSQLException((SQLException) e, "deleting object from " + tableName+ " failed");
        }finally {
            closeResources(connection, statement, null);
        }
    }

    @Override
    public List getAll() {
        Connection connection = null;
        Statement statement = null;
        ResultSet rs = null;
        List<T> results = new ArrayList<>();

        try{
            connection = getConnection();
            String sql = "SELECT * FROM " + tableName;
            statement = connection.createStatement();
            rs = statement.executeQuery(sql);

            while(rs.next()){
                Object[] args = new Object[columnNames.size()];
                for(int i = 0; i < columnNames.size(); i++){
                    String columnName = columnNames.get(i);
                    args[i] = rs.getObject(columnName);
                }
                T obj = constructor.newInstance(args);

                //Set the ID
                String idColumnName;
                if (tableName.equalsIgnoreCase("admin") ||
                        tableName.equalsIgnoreCase("instructor") ||
                        tableName.equalsIgnoreCase("student")) {
                    idColumnName = "userid"; // Use "userid" for these specific tables
                } else if (tableName.equalsIgnoreCase("assignmentquiz")) {
                    idColumnName = "assignmentid";
                } else if (tableName.equalsIgnoreCase("coursemodule")) {
                    idColumnName = "moduleid";
                } else if (tableName.equalsIgnoreCase("messageforum")) {
                    idColumnName = "messageid";
                } else if (tableName.equalsIgnoreCase("moduleassignment")) {
                    idColumnName = "moduleid";
                } else if (tableName.equalsIgnoreCase("studentcourse")) {
                    idColumnName = "studentid";
                } else {
                    idColumnName = tableName.toLowerCase() + "id"; // Default logic
                }
                obj.setId(rs.getInt(idColumnName));

                results.add(obj);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            DataBaseException.handleSQLException((SQLException) e, "getAll objects failed");
        }catch (ReflectiveOperationException e){
            System.err.println("Error creating object instance");
            e.printStackTrace();
        }finally {
            closeResources(connection,statement,rs);
        }
        return results;
    }


    private Constructor<T> getConstructor() {
        return Arrays.stream(type.getDeclaredConstructors())
                .filter(c -> c.getParameterCount() == columnNames.size()) // Match exact parameter count
                .map(c -> (Constructor<T>) c)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No suitable constructor found"));
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

    protected void closeResources(Connection conn, Statement stmt, ResultSet rs) {
        try {
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
            if (conn != null) conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private String toCamelCase(String s) {
        String[] parts = s.split("_");
        StringBuilder camelCaseString = new StringBuilder(parts[0]);
        for (int i = 1; i < parts.length; i++) {
            camelCaseString.append(Character.toUpperCase(parts[i].charAt(0)))
                    .append(parts[i].substring(1));
        }
        return camelCaseString.toString();
    }

}
