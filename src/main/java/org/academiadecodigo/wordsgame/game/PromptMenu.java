package org.academiadecodigo.wordsgame.game;

import org.academiadecodigo.wordsgame.prompt.Prompt;
import org.academiadecodigo.wordsgame.prompt.scanners.MenuInputScanner;
import org.academiadecodigo.wordsgame.prompt.scanners.StringInputScanner;

public class PromptMenu<T> {

    public Integer createNewMenu(String[] options, String title, Prompt prompt) {
        MenuInputScanner menuInputScanner = new MenuInputScanner(options, title);
        return menuInputScanner.promptMenu(prompt);
    }

    public String createNewQuestion(String question, Prompt prompt) {
        StringInputScanner stringInputScanner = new StringInputScanner(question);
        return stringInputScanner.promptString(prompt);
    }
}
