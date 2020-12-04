package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {
    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();

        userService.createUsersTable();
        userService.saveUser("Ivan", "Petrov", (byte) 41);
        System.out.println("User с именем – Ivan добавлен в базу данных");
        userService.saveUser("John", "Malkovich", (byte) 42);
        System.out.println("User с именем – John добавлен в базу данных");
        userService.saveUser("Basil", "Mano", (byte) 43);
        System.out.println("User с именем – Basil добавлен в базу данных");
        userService.saveUser("Ien", "Statman", (byte) 34);
        System.out.println("User с именем – Ien добавлен в базу данных");

        for (User user : userService.getAllUsers()) {
            System.out.println(user);
        }
        userService.cleanUsersTable();
        userService.dropUsersTable();
    }
}
