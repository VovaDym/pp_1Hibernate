package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDao;
import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {

        UserService userService = new UserServiceImpl();

        userService.createUsersTable();

        userService.saveUser("Sanya", "Ivanenco,", (byte) 34);
        userService.saveUser("Senya", "Kozlov", (byte) 33);
        userService.saveUser("Alex", "Jobs", (byte) 66);
        userService.saveUser("Vasya", "Grishin", (byte) 70);

        System.out.println(userService.getAllUsers());

        userService.cleanUsersTable();

        userService.dropUsersTable();

        Util.closeSessionFactory();
    }
}
