package dev.astamur.geekbrains.lessons.lesson7.server;

import java.sql.SQLException;

public interface AuthService {
    void start();

    String getNickByLoginPass(String login, String pass) throws SQLException;

    void stop();
}
