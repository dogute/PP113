package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.HibernateException;
import org.hibernate.Session;

import java.util.List;

public class UserDaoHibernateImpl implements UserDao {

    private Session session;

    public UserDaoHibernateImpl() {


    }


    @Override
    public void createUsersTable() {
        try (Session session = Util.getSession().openSession()) {
            session.getTransaction().begin();
            session.createNativeQuery("CREATE TABLE IF NOT EXISTS user (Id BIGINT PRIMARY KEY NOT NULL AUTO_INCREMENT," +
                    " name VARCHAR(50) NOT NULL, " +
                    "lastName VARCHAR(50) NOT NULL, " +
                    "age INT)", User.class).executeUpdate();
            session.getTransaction().commit();
        } catch (HibernateException e) {
            session.getTransaction().rollback();
            e.printStackTrace();
        }
    }

    @Override
    public void dropUsersTable() {
        try (Session session = Util.getSession().openSession()) {
            session.getTransaction().begin();
            session.createNativeQuery("DROP table IF EXISTS users", User.class).executeUpdate();
            session.getTransaction().commit();
        } catch (HibernateException e) {
            session.getTransaction().rollback();
            e.printStackTrace();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (Session session = Util.getSession().openSession()) {
            session.getTransaction().begin();
            session.save(new User(name, lastName, age));
            System.out.println("User с именем " + name + " добавлен в БД");
            session.getTransaction().commit();
        } catch (HibernateException e) {
            session.getTransaction().rollback();
            e.printStackTrace();
        }

    }

    @Override
    public void removeUserById(long id) {
        try (Session session = Util.getSession().openSession()) {
            session.getTransaction().begin();
            session.delete(session.get(User.class, id));
            session.getTransaction().commit();
        } catch (HibernateException e) {
            session.getTransaction().rollback();
            e.printStackTrace();
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> resultList = null;
        try (Session session = Util.getSession().getCurrentSession()) {
            try {
                session.getTransaction().begin();
                resultList = session.createQuery("SELECT i from User i", User.class).getResultList();
                session.getTransaction().commit();
            } catch (HibernateException e) {
                session.getTransaction().rollback();
                e.printStackTrace();
            }
        }
        return resultList;
    }

    @Override
    public void cleanUsersTable() {
        try (Session session = Util.getSession().getCurrentSession()) {
            session.getTransaction().begin();
            session.createNativeQuery("DELETE FROM users").executeUpdate();
            session.getTransaction().commit();
        } catch (HibernateException e) {
            session.getTransaction().rollback();
            e.printStackTrace();
        }
    }
}
