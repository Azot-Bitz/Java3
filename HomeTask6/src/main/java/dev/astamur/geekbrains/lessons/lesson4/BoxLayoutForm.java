package dev.astamur.geekbrains.lessons.lesson4;

import javax.swing.*;
import java.awt.*;

public class BoxLayoutForm extends JFrame {
    public BoxLayoutForm() {
        setTitle("Simple BoxLayout Window");
        setBounds(500, 500, 500, 500);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        setMinimumSize(new Dimension(100, 100));

        //setLayout(new BoxLayout(getContentPane(), BoxLayout.X_AXIS));
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        JButton[] jbs = new JButton[10];
        for (int i = 0; i < jbs.length; i++) {
            jbs[i] = new JButton("#" + i);
            if (i % 2 == 0) {
                jbs[i].setAlignmentY(Component.TOP_ALIGNMENT);
                jbs[i].setAlignmentX(Component.LEFT_ALIGNMENT);
            } else {
                jbs[i].setAlignmentY(Component.BOTTOM_ALIGNMENT);
                jbs[i].setAlignmentX(Component.RIGHT_ALIGNMENT);
            }
            add(jbs[i]);
        }

        setVisible(true);
    }
}