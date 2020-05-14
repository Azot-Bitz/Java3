package dev.astamur.geekbrains.lessons.lesson4;

import javax.swing.*;
import java.awt.*;

public class GridLayoutForm extends JFrame {
    public GridLayoutForm() {
        setTitle("Simple GridLayout Window");
        setBounds(500, 500, 500, 500);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(new GridLayout(4, 3));

        JButton[] jbs = new JButton[10];
        for (int i = 0; i < jbs.length; i++) {
            jbs[i] = new JButton("#" + i);
            add(jbs[i]);
        }

        setVisible(true);
    }
}