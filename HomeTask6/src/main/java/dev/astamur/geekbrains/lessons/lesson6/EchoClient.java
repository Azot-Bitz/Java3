package dev.astamur.geekbrains.lessons.lesson6;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.Closeable;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class EchoClient extends JFrame {
    private static final String SERVER_ADDR = "localhost";
    private static final int SERVER_PORT = 8090;
    private static final String END_MESSAGE = "/end";

    private JTextField msgInputField;
    private JTextArea chatArea;

    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;

    public EchoClient() {
        try {
            System.out.println("Open connection. Thread: " + Thread.currentThread().getName());
            openConnection();
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Ошибка соединения с сервером. " + e.getMessage(),
                    "Ошибка", JOptionPane.ERROR_MESSAGE);
        }
        prepareGUI();
    }

    public void openConnection() throws IOException {
        socket = new Socket(SERVER_ADDR, SERVER_PORT);
        in = new DataInputStream(socket.getInputStream());
        out = new DataOutputStream(socket.getOutputStream());
        new Thread(() -> {
            System.out.println("Processing connection. Thread: " + Thread.currentThread().getName());
            try {
                while (true) {
                    String strFromServer = in.readUTF();
                    if (strFromServer.equalsIgnoreCase(END_MESSAGE)) {
                        break;
                    }
                    chatArea.append(strFromServer);
                    chatArea.append("\n");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    private void close(Closeable... objects) {
        for (Closeable o : objects) {
            try {
                if (o != null) {
                    o.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void sendMessage() {
        if (!msgInputField.getText().trim().isEmpty()) {
            if (out == null) {
                JOptionPane.showMessageDialog(null, "Ошибка отправки сообщения. Сервер не доступен. ",
                        "Ошибка", JOptionPane.ERROR_MESSAGE);
            }

            String text = msgInputField.getText();

            try {
                out.writeUTF(text);
                out.flush();
                msgInputField.setText("");
                msgInputField.grabFocus();

                if (END_MESSAGE.equals(text)) {
                    close(in, out, socket);
                }
            } catch (IOException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Ошибка отправки сообщения. " + e.getMessage(),
                        "Ошибка", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public void prepareGUI() {
        // Параметры окна
        setBounds(600, 300, 500, 500);
        setTitle("Клиент");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        // Текстовое поле для вывода сообщений
        chatArea = new JTextArea();
        chatArea.setEditable(false);
        chatArea.setLineWrap(true);
        add(new JScrollPane(chatArea), BorderLayout.CENTER);

        // Нижняя панель с полем для ввода сообщений и кнопкой отправки сообщений
        JPanel bottomPanel = new JPanel(new BorderLayout());
        JButton btnSendMsg = new JButton("Отправить");
        bottomPanel.add(btnSendMsg, BorderLayout.EAST);
        msgInputField = new JTextField();
        add(bottomPanel, BorderLayout.SOUTH);
        bottomPanel.add(msgInputField, BorderLayout.CENTER);
        btnSendMsg.addActionListener(e -> sendMessage());
        msgInputField.addActionListener(e -> sendMessage());

        // Настраиваем действие на закрытие окна
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                try {
                    if (out != null) {
                        out.writeUTF(END_MESSAGE);
                    }
                } catch (IOException exc) {
                    exc.printStackTrace();
                } finally {
                    close(in, out, socket);
                }
            }
        });

        setVisible(true);
    }

    public static void main(String... args) {
        System.out.println("In main. Thread: " + Thread.currentThread().getName());
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                System.out.println("Create client. Thread: " + Thread.currentThread().getName());
                new EchoClient();
            }
        });
    }
}
