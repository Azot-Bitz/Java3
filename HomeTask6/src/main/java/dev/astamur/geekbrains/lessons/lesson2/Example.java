package dev.astamur.geekbrains.lessons.lesson2;

import java.util.Random;

public class Example {
    private static final Random rand = new Random(System.currentTimeMillis());

    public static void main(String[] args) {
        int requiredSize = 4;

        // Valid example
        String[][] matrix = generate(requiredSize, requiredSize);
        process(matrix, requiredSize);

        // Invalid size
        matrix = generate(5, 7);
        process(matrix, requiredSize);

        // Invalid data
        matrix = generate(requiredSize, requiredSize);
        matrix[2][3] = "abc";
        process(matrix, requiredSize);
    }

    private static void process(String[][] matrix, int size) {
        try {
            validateSize(matrix, size);
            System.out.println("Matrix sum is " + sum(matrix));
        } catch (MySizeArrayException e) {
            System.out.println("Illegal size of matrix. " + e.getMessage());
        } catch (MyArrayDataException e) {
            System.out.println("Illegal data in matrix. " + e.getMessage());
        }
    }

    private static int sum(String[][] matrix) {
        int sum = 0;

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                try {
                    sum += Integer.parseInt(matrix[i][j]);
                } catch (NumberFormatException e) {
                    throw new MyArrayDataException(String.format("Illegal number format '%s' at row %d and column %d",
                            matrix[i][j], i, j));
                }
            }
        }

        return sum;
    }

    private static void validateSize(String[][] matrix, int expectedSize) {
        if (matrix.length != expectedSize) {
            throw new MySizeArrayException(String.format("Expected %d lines in matrix. Actual amount %d",
                    expectedSize, matrix.length));
        }

        for (int i = 0; i < matrix.length; i++) {
            if (matrix[i].length != expectedSize) {
                throw new MySizeArrayException(String.format("Expected row %d has length %d. Actual length %d",
                        i, expectedSize, matrix[i].length));
            }
        }
    }


    private static String[][] generate(int rows, int columns) {
        String[][] result = new String[rows][columns];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                result[i][j] = String.valueOf(rand.nextInt(100));
            }
        }

        return result;
    }
}
