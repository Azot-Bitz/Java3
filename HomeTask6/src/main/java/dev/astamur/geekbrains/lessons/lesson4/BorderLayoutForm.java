package dev.astamur.geekbrains.lessons.lesson4;

import javax.swing.*;
import java.awt.*;

public class BorderLayoutForm extends JFrame {
    public BorderLayoutForm() {
        setTitle("Simple BorderLayout Window");
        setBounds(500, 500, 500, 500);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        setLayout(new BorderLayout());

//        JButton[] jbs = new JButton[5];
//        for (int i = 0; i < 5; i++) {
//            jbs[i] = new JButton("#" + i);
//        }
//
//        add(jbs[0], BorderLayout.EAST);
//        add(jbs[1], BorderLayout.WEST);
//        add(jbs[2], BorderLayout.SOUTH);
//        add(jbs[3], BorderLayout.NORTH);
//        add(jbs[4], BorderLayout.CENTER);

        JButton button = new JButton("Button 1 (PAGE_START)");
        add(button, BorderLayout.PAGE_START);

        button = new JButton("Button 2 (CENTER)");
        add(button, BorderLayout.CENTER);

        button = new JButton("Button 3 (LINE_START)");
        button.setPreferredSize(new Dimension(300, 300));
        add(button, BorderLayout.LINE_START);

        button = new JButton("Long-Named Button 4 (PAGE_END)");
        add(button, BorderLayout.PAGE_END);

        button = new JButton("5 (LINE_END)");
        add(button, BorderLayout.LINE_END);

        setVisible(true);
    }
}