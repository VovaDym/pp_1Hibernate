package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.Query;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

public class UserDaoHibernateImpl implements UserDao {
    private Transaction transaction = null;
    private Session session = Util.getSessionFactory().openSession();

    public UserDaoHibernateImpl() {

    }

    // Вспомогательный метод для выполнения операций в рамках транзакции
    private void executeInsideTransaction(Consumer<Session> action) {
        try {
            transaction = session.beginTransaction();
            action.accept(session);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            throw new RuntimeException(e);
        }
    }

    // Вспомогательный метод для выполнения операций и возврата значения в рамках транзакции
    private <T> T executeInsideTransaction(Function<Session, T> action) {
        T result = null;
        try {
            transaction = session.beginTransaction();
            result = action.apply(session);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            throw new RuntimeException(e);
        }
        return result;
    }

    @Override
    public void createUsersTable() {
        executeInsideTransaction(session -> {
            String sql = "CREATE TABLE IF NOT EXISTS users (" +
                    "id BIGINT NOT NULL AUTO_INCREMENT," +
                    "name VARCHAR(255)," +
                    "lastName VARCHAR(255)," +
                    "age TINYINT," +
                    "PRIMARY KEY (id)" +
                    ")";
            Query query = session.createNativeQuery(sql);
            query.executeUpdate();
        });
    }

    @Override
    public void dropUsersTable() {
        executeInsideTransaction(session -> {
            String sql = "DROP TABLE IF EXISTS users";
            Query query = session.createNativeQuery(sql);
            query.executeUpdate();
        });
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        executeInsideTransaction(session -> {
            User user = new User(name, lastName, age);
            session.save(user);
        });
    }

    @Override
    public void removeUserById(long id) {
        executeInsideTransaction(session -> {
            User user = session.get(User.class, id);
            if (user != null) {
                session.delete(user);
            }
        });
    }

    @Override
    public List<User> getAllUsers() {
        return executeInsideTransaction(session -> {
            return session.createQuery("from User", User.class).list();
        });
    }

    @Override
    public void cleanUsersTable() {
        executeInsideTransaction(session -> {
            String sql = "DELETE FROM users";
            Query query = session.createNativeQuery(sql);
            query.executeUpdate();
        });
    }
}
