package org.academiadecodigo.wordsgame.prompt;

import java.io.*;
import java.util.Scanner;

/**
 * Custom Prompt implementation to replace the external PromptView library.
 * Handles input/output operations for console-based interactions.
 */
public class Prompt {

    private final Scanner scanner;
    private final PrintStream output;

    public Prompt(InputStream input, PrintStream output) {
        this.scanner = new Scanner(input);
        this.output = output;
    }

    /**
     * Displays a message and waits for string input
     */
    public String prompt(String message) {
        output.print(message);
        output.flush();
        return scanner.nextLine().trim();
    }

    /**
     * Displays a message and waits for integer input
     */
    public int promptInt(String message) {
        while (true) {
            try {
                output.print(message);
                output.flush();
                String input = scanner.nextLine().trim();
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                output.println("Please enter a valid number.");
            }
        }
    }

    /**
     * Displays a menu and returns the selected option
     */
    public int promptMenu(String[] options, String title) {
        while (true) {
            output.println(title);
            for (int i = 0; i < options.length; i++) {
                output.printf("%d - %s%n", i + 1, options[i]);
            }

            try {
                output.print("Choose an option: ");
                output.flush();
                String input = scanner.nextLine().trim();
                int choice = Integer.parseInt(input);

                if (choice >= 1 && choice <= options.length) {
                    return choice;
                }
                output.println("Invalid option. Please choose between 1 and " + options.length);
            } catch (NumberFormatException e) {
                output.println("Please enter a valid number.");
            }
        }
    }

    /**
     * Displays a simple message
     */
    public void display(String message) {
        output.println(message);
    }

    /**
     * Closes the scanner
     */
    public void close() {
        scanner.close();
    }
}
