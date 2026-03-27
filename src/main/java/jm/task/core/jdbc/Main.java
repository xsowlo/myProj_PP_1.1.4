package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;


public class Main {
    private final static UserService userService = new UserServiceImpl();


    public static void main(String[] args) {

        userService.createUsersTable();
        userService.saveUser("Second","Probnek", (byte) 20);
        userService.saveUser("Ilom","Kask", (byte) 44);
        userService.saveUser("Veliki","Gatsby", (byte) 100);
        userService.saveUser("Ron","Swanson", (byte) 2);
        userService.getAllUsers();
        userService.cleanUsersTable();
        userService.dropUsersTable();

        Util.shutdown();


    }
}
