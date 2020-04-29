package dev.astamur.geekbrains.lessons.lesson7.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.*;

public class ClientHandler {
    private MyServer myServer;
    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;
    private static final long TIMEOUT = 120000;
    private static Connection connection;
    private static Statement statement;
    private static PreparedStatement preparedStatement;
    public static ResultSet resultSet;

    private String name;

    public String getName() {
        return name;
    }

    public ClientHandler(MyServer myServer, Socket socket) {
        try {
            this.myServer = myServer;
            this.socket = socket;
            this.in = new DataInputStream(socket.getInputStream());
            this.out = new DataOutputStream(socket.getOutputStream());
            this.name = "";

            new Thread(() -> {
                try{
                    Thread.sleep(TIMEOUT);
                    if(name == "") {
                        System.out.println("Timeout for Client");
                        this.in.close();
                        this.out.close();
                        this.socket.close();
                    }
                    }catch (InterruptedException | IOException e1){
                    e1.printStackTrace();
                }
            }).start();

            new Thread(() -> {
                try {
                    //connection();
                    //selectFromDB();
                    authentication();
                    readMessages();
                } catch (IOException | SQLException e) {
                    e.printStackTrace();
                //} catch (SQLException e) {
                   // e.printStackTrace();
                //} catch (ClassNotFoundException e) {
                   // e.printStackTrace();
                } finally {
                    closeConnection();
                    //disconnect();
                }
            }).start();
        } catch (IOException e) {
            throw new RuntimeException("Проблемы при создании обработчика клиента");
        }
    }

    public void authentication() throws IOException, SQLException {
        while (true) {
            String str = in.readUTF();
            if (str.startsWith("/auth")) {
                String[] parts = str.split("\\s");
                String nick = parts [3];
                //statement.executeUpdate("INSERT INTO students (nick) VALUES ('nick');");
//                preparedStatement = connection.prepareStatement("INSERT INTO 'users' ('nick') VALUES (?); ");
//                preparedStatement.setString(1, nick);
//                preparedStatement.addBatch();
                //String nick = myServer.getAuthService().getNickByLoginPass(parts[1], parts[2]);
                if (nick != null) {
                    if (!myServer.isNickBusy(nick)) {
                        sendMsg("/authok " + nick);
                        name = nick;
                        myServer.broadcastMsg(name + " зашел в чат");
                        myServer.subscribe(this);
                        return;
                    } else {
                        sendMsg("Учетная запись уже используется");
                    }
                } else {
                    sendMsg("Неверные логин/пароль");
                }
            }
        }
    }

    public void readMessages() throws IOException {
        while (true) {
            String strFromClient = in.readUTF();
            System.out.println("от " + name + ": " + strFromClient);
            if (strFromClient.equals("/end")) {
                return;
            }

            if (strFromClient.startsWith("/w")) {
                String[] tokens = strFromClient.split("\\s");
                String nick = tokens[1];
                String msg = strFromClient.substring(4 + nick.length());
                myServer.sendMsgToClient(name, nick, msg);
            } else {
                myServer.broadcastMsg(name, strFromClient);
            }
        }
    }

    public void sendMsg(String msg) {
        try {
            out.writeUTF(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void closeConnection() {
        myServer.unsubscribe(this);
        myServer.broadcastMsg(name + " вышел из чата");
        try {
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void connection() throws ClassNotFoundException, SQLException {
        Class.forName("org.sqlite.JDBC");
        connection = DriverManager.getConnection("jdbc:sqlite:main.db");
        statement = connection.createStatement();
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
    private static void selectFromDB() throws SQLException {
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
            System.out.println("ID = " + id);
            System.out.println("login = " + login);
            System.out.println("pass = " + pass);
            System.out.println("nick = " + nick);
           /* System.out.println(
                    resultSet.getInt(1) + " | " +
                            resultSet.getString("login") + " | " +
                            resultSet.getString("pass") + " | " +
                            resultSet.getString("nick")
            );
            */

        } while (resultSet.next());


    }
}
