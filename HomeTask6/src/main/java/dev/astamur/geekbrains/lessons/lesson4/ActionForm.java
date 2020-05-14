package dev.astamur.geekbrains.lessons.lesson4;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class ActionForm extends JFrame {
    private final JTextField textField = new JTextField();

    public ActionForm() {
        setTitle("Action Window");
        setBounds(500, 500, 500, 50);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(new GridBagLayout());

        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.9;
        c.gridx = 0;
        c.gridy = 0;
        textField.addActionListener(this::reactToAction);
        textField.addKeyListener(new CustomKeyListener());
        add(textField, c);

        JButton button = new JButton("Say");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.1;
        c.gridx = 1;
        c.gridy = 0;
        button.addActionListener(this::reactToAction);
        add(button, c);

        setVisible(true);
    }

    private void reactToAction(ActionEvent event) {
        JOptionPane.showMessageDialog(null, textField.getText());
    }

    private static class CustomKeyListener extends KeyAdapter {
        @Override
        public void keyTyped(KeyEvent e) {
            System.out.print(e.getKeyChar());
        }
    }
}