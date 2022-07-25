package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDao;
import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.model.User;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {

        User user1 = new User("Test", "Test", (byte) 12);
        User user2 = new User("Hey", "Man", (byte) 51);
        User user3 = new User("Waz", "Up", (byte) 11);

        UserDao userDao = new UserDaoJDBCImpl();

        try {
            userDao.createUsersTable();

            userDao.saveUser(user1.getName(), user1.getLastName(), user1.getAge());
            System.out.println("User с именем – " + user1.getName() + " добавлен в базу данных");
            userDao.saveUser(user2.getName(), user2.getLastName(), user2.getAge());
            System.out.println("User с именем – " + user2.getName() + " добавлен в базу данных");
            userDao.saveUser(user3.getName(), user3.getLastName(), user3.getAge());
            System.out.println("User с именем – " + user3.getName() + " добавлен в базу данных");


            for (User user : userDao.getAllUsers()) {
                System.out.println(user.toString());
            }
            userDao.cleanUsersTable();
            userDao.dropUsersTable();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

