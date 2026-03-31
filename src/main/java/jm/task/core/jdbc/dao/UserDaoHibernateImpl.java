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
            Transaction transaction = session.beginTransaction();

            String sql = "CREATE TABLE IF NOT EXISTS users (" +
                    "id BIGINT PRIMARY KEY AUTO_INCREMENT, " +
                    "name VARCHAR(255), " +
                    "lastName VARCHAR(255), " +
                    "age TINYINT)";

            session.createNativeQuery(sql).executeUpdate();

            transaction.commit();

        } catch (Exception e) {
            System.err.println("Ошибка выполнения операции <Создание таблицы>" + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void dropUsersTable() {
        try (Session session = sessionFactory.openSession()){
            Transaction transaction = session.beginTransaction();

            String sql = "DROP TABLE IF EXISTS users";
            session.createNativeQuery(sql).executeUpdate();

            transaction.commit();

        } catch (Exception e) {
            System.err.println("Ошибка выполнения операции <Удаление таблицы>" + e.getMessage());
            e.printStackTrace();
        }

    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()){
            transaction = session.beginTransaction();

            User user = new User();
            user.setName(name);
            user.setLastName(lastName);
            user.setAge(age);

            session.save(user);
            transaction.commit();

            System.out.println("User с именем – " + name + " добавлен в базу данных");

        } catch (Exception e) {
            if(transaction != null) {
            transaction.rollback();}
            System.err.println("Ошибка выполнения операции <Создание пользователя>" + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void removeUserById(long id) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()){
            transaction = session.beginTransaction();

            User user = session.get(User.class, id);
            if (user != null) {
                session.delete(user);
            }
            transaction.commit();

        } catch (Exception e) {
            if(transaction != null) {
            transaction.rollback();}
            System.err.println("Ошибка выполнения операции <Удаление пользователя>" + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = null;
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            users = session.createQuery("FROM User", User.class).list();
            transaction.commit();

            for (User user : users) {
                System.out.println(user);
            }
        } catch (Exception e) {
            System.err.println("Ошибка выполнения операции <Предоставление всех пользователей>" + e.getMessage());
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public void cleanUsersTable() {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();

            String hql = "DELETE FROM User";
            session.createQuery(hql).executeUpdate();
            transaction.commit();

        } catch (Exception e) {
            if(transaction != null) {
                transaction.rollback();
            }
            System.err.println("Ошибка выполнения операции <Очистка таблицы>" + e.getMessage());
            e.printStackTrace();
        }
    }

}