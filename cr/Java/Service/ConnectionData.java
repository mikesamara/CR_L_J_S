package cr.Java.Service;

import cr.Java.Animals.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class ConnectionData {

    private static final String SCRIPT_ANIMALS = "src/main/resources/script.sql";
    private static final String INSERT_ANIMAL = "(name, type_id, birthday, commands) VALUE (?, ?, ?, ?);";

    private static final String UPDATE_ANIMAL = "SET commands = ? WHERE name = ? AND birthday = ?";
    private static final String COUNT_ANIMAL = "SELECT SUM(T.cnt) AS count FROM (\n" +
            "SELECT COUNT(*) AS cnt FROM cats UNION ALL SELECT COUNT(*) AS cnt FROM dogs UNION ALL SELECT COUNT(*) AS cnt FROM hamsters) AS T\n" +
            "UNION ALL SELECT SUM(T.cnt) AS count FROM (SELECT COUNT(*) AS cnt FROM horses UNION ALL SELECT COUNT(*) AS cnt FROM donkeys\n" +
            "UNION ALL SELECT COUNT(*) AS cnt FROM camels) AS T;";

    private Connection getConnection() {
        String url = "";
        String username = "";
        String password = "";

        FileInputStream fis;
        Properties properties = new Properties();

        try {
            fis = new FileInputStream("bin/cr/SQL/user.properties");
            properties.load(fis);

            url = properties.getProperty("url");
            username = properties.getProperty("username");
            password = properties.getProperty("password");

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        Connection connection = null;

        try {
            connection = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            System.out.println(e.getSQLState());
        }
        return connection;
    }

    public String readFile(String path, Charset encoding) throws IOException {
        return Files.readString(Paths.get(path), encoding);
    }

    public List<Animal> getAnimalData(String query1, String query2) {
        List<Animal> animals = new ArrayList<>();

        String script = null;
        try {
            script = readFile(SCRIPT_ANIMALS, StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query1 + script + "\n" + query2)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String name = resultSet.getString("name");
                String type = resultSet.getString("subclass_name");
                Date birthday = resultSet.getDate("birthday");
                String commands = resultSet.getString("commands");

                Animal animal = Validate.getTypeAnimal(type, name, birthday, commands);
                animals.add(animal);
            }

        } catch (SQLException e) {
            System.out.println(e.getSQLState());
        }
        return animals;
    }

    public void setAnimalData(Animal animal) {
        String subclass = animal.getClass().getSimpleName() + "s";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO " + subclass + " " + INSERT_ANIMAL)) {
            int type;
            if (subclass.equals("Кошка") || subclass.equals("Собака") || subclass.equals("Хомяк")) type = 1;
            else type = 2;

            preparedStatement.setString(1, animal.getName());
            preparedStatement.setInt(2, type);
            preparedStatement.setDate(3, animal.getBirthday());
            preparedStatement.setString(4, animal.getCommands().toString().replaceAll("^\\[|\\]$", ""));
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getSQLState());
        }
    }

    public void updateAnimalData(Animal animal, String query) {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query + UPDATE_ANIMAL)) {
            preparedStatement.setString(1, animal.getCommands().toString().replaceAll("^\\[|\\]$", ""));
            preparedStatement.setString(2, animal.getName());
            preparedStatement.setDate(3, animal.getBirthday());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getSQLState());
        }
    }

    public List<Integer> countAnimalData() {
        List<Integer> result = new ArrayList<>();
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(COUNT_ANIMAL)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int count = resultSet.getInt("Количество");
                result.add(count);
            }

        } catch (SQLException e) {
            System.out.println(e.getSQLState());
        }
        return result;
    }
}
