package com.github.yuriiyukh.dao;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.github.yuriiyukh.connectionmanager.ConnectionManager;
import com.github.yuriiyukh.models.Person;
import com.github.yuriiyukh.reader.PropertiesReader;

@Component
public class PersonDao {
    private ConnectionManager connectionManager;

    private PersonDao() throws DaoException {
        PropertiesReader propertiesReader = new PropertiesReader("db_configuration.properties");
        this.connectionManager = new ConnectionManager(propertiesReader.readData("driverUrl"),
                propertiesReader.readData("dbUrl"), propertiesReader.readData("dbUsername"),
                propertiesReader.readData("dbPassword"));
    }

    public List<Person> index() throws DaoException {
        List<Person> people = new ArrayList<>();
        try (Connection connection = connectionManager.getConnection();
                Statement statement = connection.createStatement()) {
            String query = "SELECT * FROM Person";
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                Person person = new Person();
                person.setId(resultSet.getInt("id"));
                person.setName(resultSet.getString("name"));
                person.setAge(resultSet.getInt("age"));
                person.setEmail(resultSet.getString("email"));
                people.add(person);

            }
        } catch (SQLException e) {
            throw new DaoException("Connection failed", e);
        }

        return people;
    }

    public Person show(int id) throws DaoException {
        String query = "SELECT * FROM person WHERE id = ?";
        Person person = null;
        try (Connection connection = connectionManager.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);

            ResultSet resultSet = statement.executeQuery();
            person = new Person();
            resultSet.next();
            person.setId(resultSet.getInt("id"));
            person.setName(resultSet.getString("name"));
            person.setAge(resultSet.getInt("age"));
            person.setEmail(resultSet.getString("email"));
        } catch (SQLException e) {
            throw new DaoException("Connection failed", e);
        }

        return person;
    }

    public void save(Person person) throws DaoException {
        String query = "INSERT INTO Person VALUES (1, ?, ?, ?)";

        try (Connection connection = connectionManager.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, person.getName());
            statement.setInt(2, person.getAge());
            statement.setString(3, person.getEmail());

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Connection failed", e);
        }
    }

    public void update(int id, Person updatedPerson) throws DaoException {
        String query = "UPDATE Person SET name = ?, age = ?, email = ? WHERE id = ?";
        try (Connection connection = connectionManager.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, updatedPerson.getName());
            statement.setInt(2, updatedPerson.getAge());
            statement.setString(3, updatedPerson.getEmail());
            statement.setInt(4, id);
            statement.executeUpdate();
            
        } catch (

        SQLException e) {
            throw new DaoException("Connection failed", e);
        }
    }

    public void delete(int id) throws DaoException {
        String query = "DELETE FROM Person WHERE id = ?";
        try (Connection connection = connectionManager.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            statement.executeUpdate();
            
        } catch (

        SQLException e) {
            throw new DaoException("Connection failed", e);
        }
    }
}
