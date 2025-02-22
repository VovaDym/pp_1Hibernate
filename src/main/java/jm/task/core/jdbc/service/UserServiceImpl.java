package jm.task.core.jdbc.service;

import jm.task.core.jdbc.dao.UserDao;
import jm.task.core.jdbc.dao.UserDaoHibernateImpl;
import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;



public class UserServiceImpl  implements UserService {

    UserDao  dao = new UserDaoHibernateImpl();

    public UserServiceImpl() throws SQLException {
    }


    public void createUsersTable() throws SQLException {
        dao.createUsersTable();
    }

    public void dropUsersTable() {
        dao.dropUsersTable();
    }

    public void saveUser(String name, String lastName, byte age) {
        dao.saveUser(name, lastName, age);
    }

    public void removeUserById(long id) throws SQLException {
        dao.removeUserById(id);
    }

    public List<User> getAllUsers() throws SQLException {
        return dao.getAllUsers();
    }

    public void cleanUsersTable() {
         dao.cleanUsersTable();
    }
}
