package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private final String DROP_SQL = "DROP TABLE IF EXISTS test.users";
    private final String CREATE_TABLE_SQL = "CREATE TABLE IF NOT EXISTS test.users ( " +
            "`user_id` INT NOT NULL AUTO_INCREMENT," +
            "`name` VARCHAR(45) NOT NULL," +
            "`last_name` VARCHAR(45) NOT NULL," +
            "`age` INT UNSIGNED NOT NULL," +
            "PRIMARY KEY (`user_id`))";
    private final String SAVE_SQL = "INSERT INTO test.users (name, last_name, age) VALUES (?,  ?,  ?)";
    private final String REMOVE_BY_ID_SQL = "DELETE FROM test.users WHERE user_id = ?";
    private final String GET_ALL = "SELECT users.* FROM test.users";
    private final String CLEAN_TABLE_SQL = "DELETE FROM test.users";

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        try (Statement s = Util.getConnection().createStatement()) {
            s.execute(CREATE_TABLE_SQL);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void dropUsersTable() {
        try (Statement s = Util.getConnection().createStatement()) {
            s.execute(DROP_SQL);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try (PreparedStatement s = Util.getConnection().prepareStatement(SAVE_SQL)) {
            s.setString(1, name);
            s.setString(2, lastName);
            s.setInt(3, age);
            s.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        try (PreparedStatement s = Util.getConnection().prepareStatement(REMOVE_BY_ID_SQL)) {
            s.setLong(1, id);
            s.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();

        try (Statement s = Util.getConnection().createStatement()) {
            ResultSet resultSet = s.executeQuery(GET_ALL);

            while (resultSet.next()) {
                User user = new User(resultSet.getString("name"),
                        resultSet.getString("last_name"),
                        resultSet.getByte("age"));
                user.setId(resultSet.getLong("user_id"));
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    public void cleanUsersTable() {
        try (Statement s = Util.getConnection().createStatement()) {
            s.executeUpdate(CLEAN_TABLE_SQL);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
