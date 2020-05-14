package dev.astamur.geekbrains.lessons.lesson7.server;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ClientHandler {
    private static final Logger LOGGER = LogManager.getLogger(ClientHandler.class);
    private MyServer myServer;
    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;
    private static final long TIMEOUT = 120000;


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
            ExecutorService executorService = Executors.newFixedThreadPool(2);

            Thread t1 = new Thread(() -> {
                executorService.execute(() -> {

                            for (;;) {
                                if (Thread.currentThread().isInterrupted()) {
                                    break;
                                }
                                try {
                                    Thread.sleep(TIMEOUT);
                                    if(name == "") {
                                        LOGGER.error("Timeout for Client");
                                        this.in.close();
                                        this.out.close();
                                        this.socket.close();
                                    }
                                } catch (InterruptedException | IOException e) {
                                   LOGGER.error("Timeout for client", e);
                                }finally {
                                    executorService.shutdown();
                                }
                            }
                        }
                );
            });

            Thread t2 = new Thread(() -> {
                executorService.execute(() -> {

                            for (;;) {
                                if (Thread.currentThread().isInterrupted()) {
                                    break;
                                }
                                try {
                                    authentication();
                                    readMessages();
                                } catch (IOException | SQLException e) {
                                    LOGGER.error("Ошибка работы приложения", e);
                                } finally {
                                    closeConnection();
                                    executorService.shutdownNow();
                                }
                                }

                        }
                );
            });
            t1.start();
            t2.start();

//            new Thread(() -> {
//                try{
//                    Thread.sleep(TIMEOUT);
//                    if(name == "") {
//                        System.out.println("Timeout for Client");
//                        this.in.close();
//                        this.out.close();
//                        this.socket.close();
//                    }
//                    }catch (InterruptedException | IOException e1){
//                    e1.printStackTrace();
//                }
//            }).start();

//            new Thread(() -> {
//                try {
//                    authentication();
//                    readMessages();
//                } catch (IOException | SQLException e) {
//                    e.printStackTrace();
//                } finally {
//                    closeConnection();
//                }
//            });
 } catch (IOException e) {
//            throw new RuntimeException("Проблемы при создании обработчика клиента");
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
                        LOGGER.info("Пользователь : {} зашел в чат", name);
                        myServer.subscribe(this);
                        return;
                    } else {
                        LOGGER.info("Учетная запись уже используется");
                    }
                } else {
                    LOGGER.info("Неверные логин/пароль");
                }
            }
        }
    }

    public void readMessages() throws IOException {
        while (true) {
            String strFromClient = in.readUTF();
            //System.out.println("от " + name + ": " + strFromClient);
            LOGGER.info("Сообщение от {} -> {}", name, strFromClient);
            if (strFromClient.equals("/end")) {
                return;
            }

            if (strFromClient.startsWith("/w")) {
                String[] tokens = strFromClient.split("\\s");
                String nick = tokens[1];
                String msg = strFromClient.substring(4 + nick.length());
                myServer.sendMsgToClient(name, nick, msg);
                LOGGER.info("Личное сообщение от {} -> {}", name, nick);
            } else {
                myServer.broadcastMsg(name, strFromClient);
            }
        }
    }

    public void sendMsg(String msg) {
        try {
            out.writeUTF(msg);
        } catch (IOException e) {
            LOGGER.error("Ошибка отправки сообщения :{}", msg ,e);
        }
    }

    public void closeConnection() {
        myServer.unsubscribe(this);
        myServer.broadcastMsg(name + " вышел из чата");
        LOGGER.info("Клиент {} вышел из чата", name);
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
    }




