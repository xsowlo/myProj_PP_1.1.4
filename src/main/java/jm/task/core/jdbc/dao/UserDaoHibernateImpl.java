package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {

    private static final SessionFactory sessionFactory = Util.getSessionFactory();



    public UserDaoHibernateImpl() {

    }

    @Override
    public void createUsersTable() {
        try (Session session = sessionFactory.openSession()){
            session.beginTransaction();

            String sql = "CREATE TABLE IF NOT EXISTS users (" +
                    "id BIGINT PRIMARY KEY AUTO_INCREMENT, " +
                    "name VARCHAR(255), " +
                    "lastName VARCHAR(255), " +
                    "age TINYINT)";

            session.createNativeQuery(sql).executeUpdate();

            session.getTransaction().commit();

        } catch (Exception e) {
            sessionFactory.getCurrentSession().getTransaction().rollback();
            e.printStackTrace();
        }
    }

    @Override
    public void dropUsersTable() {
        try (Session session = sessionFactory.openSession()){
            session.beginTransaction();

            String sql = "DROP TABLE IF EXISTS users";
            session.createNativeQuery(sql).executeUpdate();

            session.getTransaction().commit();

        } catch (Exception e) {
            sessionFactory.getCurrentSession().getTransaction().rollback();
            e.printStackTrace();
        }

    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (Session session = sessionFactory.openSession()){
            session.beginTransaction();

            User user = new User();
            user.setName(name);
            user.setLastName(lastName);
            user.setAge(age);

            session.save(user);
            session.getTransaction().commit();

            System.out.println("User с именем – " + name + " добавлен в базу данных");

        } catch (Exception e) {
            sessionFactory.getCurrentSession().getTransaction().rollback();

            e.printStackTrace();
        }
    }

    @Override
    public void removeUserById(long id) {
        try (Session session = sessionFactory.openSession()){
            session.beginTransaction();

            User user = session.get(User.class, id);
            if (user != null) {
                session.delete(user);
            }
            session.getTransaction().commit();

        } catch (Exception e) {
            sessionFactory.getCurrentSession().getTransaction().rollback();
            e.printStackTrace();
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = null;
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            users = session.createQuery("FROM User", User.class).list();
            session.getTransaction().commit();

            for (User user : users) {
                System.out.println(user);
            }
        } catch (Exception e) {
            sessionFactory.getCurrentSession().getTransaction().rollback();
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public void cleanUsersTable() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            String hql = "DELETE FROM User";
            session.createQuery(hql).executeUpdate();
            session.getTransaction().commit();

        } catch (Exception e) {
            sessionFactory.getCurrentSession().getTransaction().rollback();
            e.printStackTrace();
        }
    }

}