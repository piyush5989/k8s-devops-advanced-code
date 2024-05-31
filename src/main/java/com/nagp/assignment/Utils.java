package com.nagp.assignment;

public class Utils {
    static void printSeparator(int[] columnSizes) {
        for (int columnSize : columnSizes) {
            System.out.print("+");
            for (int j = 0; j < columnSize + 2; j++) {
                System.out.print("-");
            }
        }
        System.out.println("+");
    }

    static void printRow(String[] row, int[] columnSizes) {
        for (int i = 0; i < row.length; i++) {
            System.out.print("| ");
            System.out.print(String.format("%-" + columnSizes[i] + "s", row[i]));
            System.out.print(" ");
        }
        System.out.println("|");
    }
}
