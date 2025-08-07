package org.academiadecodigo.wordsgame.prompt.scanners;

import org.academiadecodigo.wordsgame.prompt.Prompt;

/**
 * Scanner for string-based input operations
 */
public class StringInputScanner {

    private final String message;

    public StringInputScanner(String message) {
        this.message = message;
    }

    /**
     * Prompts the user for string input
     * @param prompt the prompt instance to use for I/O
     * @return the user's input string
     */
    public String promptString(Prompt prompt) {
        return prompt.prompt(message);
    }

    public String getMessage() {
        return message;
    }
}
