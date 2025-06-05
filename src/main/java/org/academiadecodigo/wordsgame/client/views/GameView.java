package org.academiadecodigo.wordsgame.client.views;

import javax.swing.*;
import java.awt.*;

public class GameView extends JFrame {

    private JTextField messageField;
    private JButton sendButton;
    private JTextArea chatArea;

    public GameView() {
        setupFrame();
        initComponents();
    }

    private void setupFrame() {
        setTitle("Words Crush Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
    }

    private void initComponents() {
        // Initialize UI components
        messageField = new JTextField(20);
        sendButton = new JButton("Send");
        chatArea = new JTextArea();
        chatArea.setEditable(false);

        // Layout setup
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(new JScrollPane(chatArea), BorderLayout.CENTER);

        JPanel messagePanel = new JPanel(new FlowLayout());
        messagePanel.add(messageField);
        messagePanel.add(sendButton);
        panel.add(messagePanel, BorderLayout.SOUTH);

        add(panel);
    }

    // Getters for components
    public JTextField getMessageField() {
        return messageField;
    }

    public JButton getSendButton() {
        return sendButton;
    }

    public JTextArea getChatArea() {
        return chatArea;
    }
}
