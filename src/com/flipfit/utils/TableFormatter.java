
package com.flipfit.utils;

import java.util.ArrayList;
import java.util.List;

public class TableFormatter {

    /**
     * Prints a well-formatted table to the console.
     * @param headers An array of strings for the table headers.
     * @param data A list of string arrays, where each inner array represents a row.
     */
    public static void printTable(String[] headers, List<String[]> data) {
        if (headers == null || headers.length == 0) {
            System.out.println("No headers provided for the table.");
            return;
        }

        // 1. Calculate column widths
        int[] columnWidths = new int[headers.length];
        for (int i = 0; i < headers.length; i++) {
            columnWidths[i] = headers[i].length();
        }

        for (String[] row : data) {
            if (row.length > headers.length) { // Handle rows with more columns than headers
                // You might want to log a warning or adjust behavior
                // For now, let's just consider up to headers.length columns
            }
            for (int i = 0; i < row.length && i < headers.length; i++) {
                if (row[i] != null && row[i].length() > columnWidths[i]) {
                    columnWidths[i] = row[i].length();
                }
            }
        }

        // 2. Print top border
        printBorder(columnWidths);

        // 3. Print headers
        System.out.print("|");
        for (int i = 0; i < headers.length; i++) {
            // Apply bold blue to headers
            System.out.printf(" " + ConsoleFormatter.BOLD_BLUE + "%-" + columnWidths[i] + "s" + ConsoleFormatter.RESET + " |", headers[i]);
        }
        System.out.println();

        // 4. Print separator
        printBorder(columnWidths);

        // 5. Print data rows
        if (data.isEmpty()) {
            System.out.print("|");
            int totalWidth = 0;
            for(int width : columnWidths) totalWidth += width + 3; // +3 for " | "
            totalWidth -= 1; // remove last " "
            String emptyMessage = "No data available";
            int padding = (totalWidth - emptyMessage.length()) / 2;
            System.out.printf("%" + padding + "s%s%" + (totalWidth - padding - emptyMessage.length()) + "s", "", emptyMessage, "");
            System.out.println("|");
        } else {
            for (String[] row : data) {
                System.out.print("|");
                for (int i = 0; i < row.length; i++) {
                    System.out.printf(" %-" + columnWidths[i] + "s |", row[i]);
                }
                System.out.println();
            }
        }

        // 6. Print bottom border
        printBorder(columnWidths);
    }

    private static void printBorder(int[] columnWidths) {
        System.out.print("+");
        for (int width : columnWidths) {
            for (int i = 0; i < width + 2; i++) { // +2 for padding spaces
                System.out.print("-");
            }
            System.out.print("+");
        }
        System.out.println();
    }

    /**
     * Prints a menu in a boxed, formatted manner with colors.
     * @param title The title of the menu.
     * @param options A list of strings, where each string is a menu option.
     */
    public static void printBoxedMenu(String title, List<String> options) {
        // Calculate max width for formatting
        int maxWidth = title.length();
        for (String option : options) {
            // Account for numbering "X. "
            if ((option.length() + 3) > maxWidth) {
                maxWidth = option.length() + 3;
            }
        }
        maxWidth += 4; // Add padding for box (e.g., "| " and " |")

        // Ensure minimum width for aesthetics
        if (maxWidth < 30) {
            maxWidth = 30;
        }

        String horizontalLine = ConsoleFormatter.BOLD_CYAN + "+" + "-".repeat(maxWidth) + "+" + ConsoleFormatter.RESET;

        System.out.println(horizontalLine);
        // Print title, centered and bold purple
        System.out.println(ConsoleFormatter.BOLD_CYAN + "| " + ConsoleFormatter.BOLD_PURPLE +
                String.format("%-" + (maxWidth - 2) + "s", centerString(title, maxWidth - 2)) +
                ConsoleFormatter.RESET + ConsoleFormatter.BOLD_CYAN + " |" + ConsoleFormatter.RESET);
        System.out.println(horizontalLine);

        // Print options
        for (int i = 0; i < options.size(); i++) {
            String optionText = (i + 1) + ". " + options.get(i);
            System.out.println(ConsoleFormatter.BOLD_CYAN + "| " + ConsoleFormatter.RESET +
                    String.format("%-" + (maxWidth - 2) + "s", optionText) +
                    ConsoleFormatter.BOLD_CYAN + " |" + ConsoleFormatter.RESET);
        }
        System.out.println(horizontalLine);
    }

    // Helper method to center a string within a given width
    private static String centerString(String s, int width) {
        if (s == null) return "";
        if (s.length() >= width) return s;
        int padding = (width - s.length()) / 2;
        return " ".repeat(padding) + s + " ".repeat(width - s.length() - padding);
    }
}