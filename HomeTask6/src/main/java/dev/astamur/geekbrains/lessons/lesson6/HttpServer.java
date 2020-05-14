package dev.astamur.geekbrains.lessons.lesson6;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class HttpServer {
    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(8080)) {
            System.out.println("Server started!");

            while (true) {
                // Ожидаем подключения
                Socket socket = serverSocket.accept();
                System.out.println("Client connected!");

                // Создаем потоки чтения и записи
                try (BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
                     PrintWriter output = new PrintWriter(socket.getOutputStream())) {

                    // Ждем первой строки запроса
                    while (!input.ready()) ;

                    // Считываем и печатаем все что было отправлено клиентом
                    System.out.println();
                    while (input.ready()) {
                        System.out.println(input.readLine());
                    }

                    // Отправляем ответ
                    output.println("HTTP/1.1 200 OK");
                    output.println("Content-Type: text/html; charset=utf-8");
                    output.println();
                    output.println(loadIndex());
                    output.flush();

                    // По окончанию выполнения блока try-with-resources потоки, а вместе с ними и соединение, будут закрыты
                    System.out.println("Client disconnected!");
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private static String loadIndex() {
        try {
            URI fileUri = HttpServer.class.getClassLoader().getResource("index.html").toURI();
            return new String(Files.readAllBytes(Paths.get(fileUri)));
        } catch (Exception e) {
            System.err.println("Can't read file 'index.html'. " + e.getMessage());
            return "";
        }
    }
}

