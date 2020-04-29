package dev.astamur.geekbrains.lessons.lesson7.server;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BaseAuthService implements AuthService {
    private List<Entry> entries;
    private static Connection connection;
    private static Statement statement;

    @Override
    public void start() {
        System.out.println("Сервис аутентификации запущен");
    }

    @Override
    public void stop() {
        System.out.println("Сервис аутентификации остановлен");
    }


    public BaseAuthService() throws SQLException, ClassNotFoundException {
        entries = new ArrayList<>();
        /*entries.add(new Entry("login1", "pass1", "nick1"));
        entries.add(new Entry("login2", "pass2", "nick2"));
        entries.add(new Entry("login3", "pass3", "nick3"));
    */
        Class.forName("org.sqlite.JDBC");
        connection = DriverManager.getConnection("jdbc:sqlite:main.db");
        statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM users");
        boolean hasNext = resultSet.next();
        if (!hasNext) {
            System.out.println("RS is empty");
            return;
        }
        do {
            int id = resultSet.getInt("id");
            String  login = resultSet.getString("login");
            String  pass = resultSet.getString("pass");
            String nick = resultSet.getString("nick");
            entries.add(new Entry(login, pass, nick));
    } while (resultSet.next());
    }

    private static void disconnect() {
        try {
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getNickByLoginPass(String login, String pass)  {
        for (Entry o : entries) {
            if (o.login.equals(login) && o.pass.equals(pass)) return o.nick;
       }
        return null;
    }


    private static class Entry {
        private String login;
        private String pass;
        private String nick;

        public Entry(String login, String pass, String nick) {
            this.login = login;
            this.pass = pass;
            this.nick = nick;
        }
    }
}
