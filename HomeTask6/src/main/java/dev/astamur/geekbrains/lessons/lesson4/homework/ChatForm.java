package dev.astamur.geekbrains.lessons.lesson4.homework;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class ChatForm extends JFrame {
    private static final int PADDING = 10;

    private final JTextField textField;
    private final JTextArea textArea;

    public ChatForm() {
        setTitle("Action Window");
        setBounds(500, 500, 500, 500);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(new GridBagLayout());

        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(PADDING, PADDING, PADDING, PADDING);

        textArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1;
        c.gridx = 0;
        c.gridy = 0;
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setRows(10);
        textArea.setEditable(false);
        textArea.setPreferredSize(new Dimension(350, 350));
        scrollPane.setViewportView(textArea);
        scrollPane.setWheelScrollingEnabled(true);
        add(scrollPane, c);

        textField = new JTextField();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1;
        c.gridx = 0;
        c.gridy = 1;
        textField.addActionListener(this::reactToAction);
        add(textField, c);

        JButton button = new JButton("Send");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1;
        c.gridx = 1;
        c.gridy = 1;
        button.addActionListener(this::reactToAction);
        add(button, c);

        pack();
        setVisible(true);
    }

    private void reactToAction(ActionEvent event) {
        textArea.append(String.format("> %s\n", textField.getText()));
        textField.setText("");
    }

    public static void main(String[] args) {
        new ChatForm();
    }
}