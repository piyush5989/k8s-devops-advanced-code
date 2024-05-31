package com.nagp.assignment;

public class Utils {
    static void printSeparator(StringBuilder builder, int[] columnSizes) {
        for (int columnSize : columnSizes) {
            builder.append("+");
            for (int j = 0; j < columnSize + 2; j++) {
                builder.append("-");
            }
        }
        builder.append("+");
        builder.append("\\n");
    }

    static void printRow(StringBuilder builder, String[] row, int[] columnSizes) {
        for (int i = 0; i < row.length; i++) {
            builder.append("| ");
            builder.append(String.format("%-" + columnSizes[i] + "s", row[i]));
            builder.append(" ");
        }
        builder.append("|");
        builder.append("\\n");
    }
}
