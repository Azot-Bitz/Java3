package dev.astamur.geekbrains.lessons.lesson4;

import javax.swing.*;
import java.awt.*;

public class FlowLayoutForm extends JFrame {
    public FlowLayoutForm() {
        setTitle("Simple FlowLayout Window");
        setBounds(500, 500, 500, 300);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        JButton[] jbs = new JButton[10];
        for (int i = 0; i < jbs.length; i++) {
            jbs[i] = new JButton("#" + i);
            add(jbs[i]);
        }

        setVisible(true);
    }
}