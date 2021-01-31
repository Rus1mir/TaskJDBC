package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;
import java.util.function.Consumer;

public class UserDaoHibernateImpl implements UserDao {
    private final String DROP_SQL = "DROP TABLE IF EXISTS test.users";
    private final String CREATE_TABLE_SQL = "CREATE TABLE IF NOT EXISTS test.users ( " +
            "`user_id` INT NOT NULL AUTO_INCREMENT," +
            "`name` VARCHAR(45) NOT NULL," +
            "`last_name` VARCHAR(45) NOT NULL," +
            "`age` INT UNSIGNED NOT NULL," +
            "PRIMARY KEY (`user_id`))";

    public UserDaoHibernateImpl() {

    }

    @Override
    public void createUsersTable() {
        executeInsideTransaction(session -> session.createNativeQuery(CREATE_TABLE_SQL).executeUpdate());
    }

    @Override
    public void dropUsersTable() {
        executeInsideTransaction(session -> session.createNativeQuery(DROP_SQL).executeUpdate());
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        executeInsideTransaction(session -> session.persist(new User(name, lastName, age)));
    }

    @Override
    public void removeUserById(long id) {
        executeInsideTransaction(session -> session.remove(session.find(User.class, id)));
    }

    @Override
    public List<User> getAllUsers() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("select u from User u", User.class).getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void cleanUsersTable() {
        executeInsideTransaction(session -> session.createNativeQuery("delete from Users").executeUpdate());
    }

    private void executeInsideTransaction(Consumer<Session> action) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            action.accept(session);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
        }
    }
}
