package Repository;

import Models.Identifiable;
import Repository.IRepository;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvException;

import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class FileRepository<T extends Identifiable> implements IRepository<T> {
    private final String fileName;


    public FileRepository(String fileName) {
        this.fileName = fileName;

    }

    @Override
    public void create(T obj) {
        doInFile(data -> data.putIfAbsent(obj.getId(), obj));

    }

    @Override
    public T get(Integer id) {
        return readDataFromFile().get(id);
    }

    @Override
    public void update(T obj) {
        doInFile(data -> data.replace(obj.getId(), obj));
    }

    @Override
    public void delete(Integer id) {
        doInFile(data -> data.remove(id));
    }

    @Override
    public List<T> getAll() {
        return readDataFromFile().values().stream().toList();
    }

    /**
     * Performs an operation on the data stored in the file.
     *
     * @param function The function to apply to the data.
     */
    private void doInFile(Consumer<Map<Integer, T>> function) {
        Map<Integer, T> data = readDataFromFile();
        function.accept(data);
        writeDataToFile(data);
    }

    /**
     * Reads the data from the file.
     *
     * @return The data stored in the file, or an empty map if the file is empty or does not exist.
     */
    private Map<Integer, T> readDataFromFile() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName))) {
            return (Map<Integer, T>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            return new HashMap<>();
        }
    }

    /**
     * Writes the data to the file.
     *
     * @param data The data to write to the file.
     */
    private void writeDataToFile(Map<Integer, T> data) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName))) {
            oos.writeObject(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}