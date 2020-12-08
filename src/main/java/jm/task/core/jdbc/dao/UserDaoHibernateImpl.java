package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    private SessionFactory sessionFactory;
    private final String DROP_SQL = "DROP TABLE IF EXISTS test.users";
    private final String CREATE_TABLE_SQL = "CREATE TABLE IF NOT EXISTS test.users ( " +
            "`user_id` INT NOT NULL," +
            "`name` VARCHAR(45) NOT NULL," +
            "`last_name` VARCHAR(45) NOT NULL," +
            "`age` INT UNSIGNED NOT NULL," +
            "PRIMARY KEY (`user_id`))";

    public UserDaoHibernateImpl() {

    }

    @Override
    public void createUsersTable() {
        Transaction transaction = null;

        try (Session session = getSessionFactory().openSession()) {
            Query query = session.createSQLQuery(CREATE_TABLE_SQL);
            transaction = session.beginTransaction();
            query.executeUpdate();
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public void dropUsersTable() {
        Transaction transaction = null;

        try (Session session = getSessionFactory().openSession()) {
            Query query = session.createSQLQuery(DROP_SQL);
            transaction = session.beginTransaction();
            query.executeUpdate();
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Transaction transaction = null;

        try (Session session = getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.persist(new User(name, lastName, age));
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public void removeUserById(long id) {
        Transaction transaction = null;

        try (Session session = getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.delete(session.get(User.class, id));
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public List<User> getAllUsers() {
        try (Session session = getSessionFactory().openSession()) {
            return session.createQuery("from User", User.class).getResultList();
        } catch (HibernateException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void cleanUsersTable() {
        Transaction transaction = null;

        try (Session session = getSessionFactory().openSession()) {
            Query query = session.createQuery("delete from User");
            transaction = session.beginTransaction();
            query.executeUpdate();
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    protected SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            sessionFactory = Util.getConfig().buildSessionFactory();
        }
        return sessionFactory;
    }
}
