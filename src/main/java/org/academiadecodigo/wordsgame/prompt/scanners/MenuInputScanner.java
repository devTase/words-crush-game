package org.academiadecodigo.wordsgame.prompt.scanners;

import org.academiadecodigo.wordsgame.prompt.Prompt;

/**
 * Scanner for menu-based input operations
 */
public class MenuInputScanner {

    private final String[] options;
    private final String title;

    public MenuInputScanner(String[] options, String title) {
        this.options = options.clone(); // Defensive copy
        this.title = title;
    }

    /**
     * Prompts the user to select an option from the menu
     * @param prompt the prompt instance to use for I/O
     * @return the selected option number (1-based)
     */
    public int promptMenu(Prompt prompt) {
        return prompt.promptMenu(options, title);
    }

    public String[] getOptions() {
        return options.clone(); // Defensive copy
    }

    public String getTitle() {
        return title;
    }
}
