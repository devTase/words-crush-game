package org.academiadecodigo.wordsgame.application.client;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.text.*;

public class Client extends JFrame implements ActionListener {
    private JTextField messageField;
    private JButton sendButton;
    private JButton disconnectButton;
    private JTextPane chatArea;
    private JLabel statusLabel;
    private JLabel connectionLabel;
    private BufferedReader in;
    private PrintWriter out;
    private boolean isConnected = false;
    private String currentUser = "Guest";

    // Modern UI Colors
    private static final Color PRIMARY_COLOR = new Color(51, 122, 183);
    private static final Color SECONDARY_COLOR = new Color(108, 117, 125);
    private static final Color SUCCESS_COLOR = new Color(40, 167, 69);
    private static final Color DANGER_COLOR = new Color(220, 53, 69);
    private static final Color BACKGROUND_COLOR = new Color(248, 249, 250);
    private static final Color CHAT_BACKGROUND = Color.WHITE;
    private static final Color BORDER_COLOR = new Color(222, 226, 230);

    public Client(String serverAddress, int serverPort) throws IOException {
        super("🎮 Words Crush Game Client");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Modern Look and Feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            // Fallback to default
        }

        initializeComponents();
        setupLayout();
        setupStyling();
        setupEventHandlers();
        connectToServer(serverAddress, serverPort);
    }

    private void initializeComponents() {
        // Chat area with modern styling
        chatArea = new JTextPane();
        chatArea.setEditable(false);
        chatArea.setBackground(CHAT_BACKGROUND);
        chatArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Message input field
        messageField = new JTextField();
        messageField.setPreferredSize(new Dimension(400, 40));
        messageField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER_COLOR, 1), BorderFactory.createEmptyBorder(5, 10, 5, 10)));
        messageField.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 14));

        // Buttons with modern styling
        sendButton = createStyledButton("📤 Send", PRIMARY_COLOR);
        disconnectButton = createStyledButton("🔌 Disconnect", DANGER_COLOR);

        // Status and connection labels
        statusLabel = new JLabel("⚪ Ready to connect");
        statusLabel.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 12));
        statusLabel.setForeground(SECONDARY_COLOR);

        connectionLabel = new JLabel("🔴 Disconnected");
        connectionLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 12));
        connectionLabel.setForeground(DANGER_COLOR);
    }

    private JButton createStyledButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 12));
        button.setPreferredSize(new Dimension(120, 40));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Hover effect
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(bgColor.darker());
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(bgColor);
            }
        });

        return button;
    }

    private void setupLayout() {
        setLayout(new BorderLayout());

        // Top panel with connection info
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(BACKGROUND_COLOR);
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 15, 5, 15));

        JPanel connectionPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        connectionPanel.setBackground(BACKGROUND_COLOR);
        connectionPanel.add(connectionLabel);
        connectionPanel.add(Box.createHorizontalStrut(20));
        connectionPanel.add(statusLabel);

        topPanel.add(connectionPanel, BorderLayout.WEST);
        topPanel.add(disconnectButton, BorderLayout.EAST);

        // Chat area with scroll
        JScrollPane chatScrollPane = new JScrollPane(chatArea);
        chatScrollPane.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(BORDER_COLOR),
                "💬 Game Chat",
                TitledBorder.LEFT,
                TitledBorder.TOP,
                new Font(Font.SANS_SERIF, Font.BOLD, 12),
                SECONDARY_COLOR));
        chatScrollPane.setPreferredSize(new Dimension(900, 500));

        // Bottom panel with message input
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBackground(BACKGROUND_COLOR);
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(10, 15, 15, 15));

        JPanel inputPanel = new JPanel(new BorderLayout(10, 0));
        inputPanel.setBackground(BACKGROUND_COLOR);
        inputPanel.add(messageField, BorderLayout.CENTER);
        inputPanel.add(sendButton, BorderLayout.EAST);

        bottomPanel.add(inputPanel, BorderLayout.CENTER);

        // Add all panels to main frame
        add(topPanel, BorderLayout.NORTH);
        add(chatScrollPane, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    private void setupStyling() {
        getContentPane().setBackground(BACKGROUND_COLOR);
        setPreferredSize(new Dimension(950, 700));
        setMinimumSize(new Dimension(800, 600));
    }

    private void setupEventHandlers() {
        sendButton.addActionListener(this);
        messageField.addActionListener(this);
        disconnectButton.addActionListener(e -> disconnect());

        // Enhanced keyboard shortcuts
        messageField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER && e.isControlDown()) {
                    messageField.setText(messageField.getText() + "\n");
                }
            }
        });
    }

    private void connectToServer(String serverAddress, int serverPort) {
        try {
            updateStatus("🔄 Connecting to server...", SECONDARY_COLOR);

            // Create socket connection
            Socket socket = new Socket(serverAddress, serverPort);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);

            isConnected = true;
            updateConnectionStatus(true, serverAddress + ":" + serverPort);
            updateStatus("✅ Connected successfully!", SUCCESS_COLOR);

            appendToChat("🎮 Welcome to Words Crush Game!", "SYSTEM", SUCCESS_COLOR);
            appendToChat("💡 Type your message and press Enter or click Send", "SYSTEM", SECONDARY_COLOR);
            appendToChat("🎯 Available commands: /help, /list, /pm [user] [message]", "SYSTEM", SECONDARY_COLOR);

            // Start input thread
            startInputThread();

        } catch (IOException e) {
            updateConnectionStatus(false, "Connection failed");
            updateStatus("❌ Connection failed: " + e.getMessage(), DANGER_COLOR);
            appendToChat("Connection failed: " + e.getMessage(), "ERROR", DANGER_COLOR);
        }
    }

    private void startInputThread() {
        Thread inputThread = new Thread(() -> {
            try {
                String message;
                while (isConnected && (message = in.readLine()) != null) {
                    final String finalMessage = message;
                    SwingUtilities.invokeLater(() -> processServerMessage(finalMessage));
                }
            } catch (IOException ex) {
                if (isConnected) {
                    SwingUtilities.invokeLater(() -> {
                        appendToChat("Connection lost: " + ex.getMessage(), "ERROR", DANGER_COLOR);
                        updateConnectionStatus(false, "Connection lost");
                        updateStatus("❌ Connection lost", DANGER_COLOR);
                    });
                }
            }
        });
        inputThread.setDaemon(true);
        inputThread.start();
    }

    private void processServerMessage(String message) {
        // Enhanced message processing
        if (message.startsWith("[SERVER]:")) {
            appendToChat(message.substring(9).trim(), "SERVER", PRIMARY_COLOR);
        } else if (message.startsWith("[ADMIN]")) {
            appendToChat(message, "ADMIN", new Color(138, 43, 226));
        } else if (message.startsWith("[ERROR]")) {
            appendToChat(message.substring(7).trim(), "ERROR", DANGER_COLOR);
        } else if (message.contains("just connected") || message.contains("just disconnected")) {
            appendToChat(message, "SYSTEM", SUCCESS_COLOR);
        } else {
            appendToChat(message, "CHAT", Color.BLACK);
        }

        // Auto-scroll to bottom
        SwingUtilities.invokeLater(() -> {
            try {
                chatArea.setCaretPosition(chatArea.getDocument().getLength());
            } catch (Exception ignored) {
            }
        });
    }

    private void appendToChat(String message, String type, Color color) {
        try {
            StyledDocument doc = chatArea.getStyledDocument();
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));

            // Create styles
            SimpleAttributeSet timeStyle = new SimpleAttributeSet();
            StyleConstants.setForeground(timeStyle, SECONDARY_COLOR);
            StyleConstants.setFontSize(timeStyle, 11);

            SimpleAttributeSet messageStyle = new SimpleAttributeSet();
            StyleConstants.setForeground(messageStyle, color);
            StyleConstants.setFontSize(messageStyle, 13);
            if (type.equals("ADMIN") || type.equals("SERVER")) {
                StyleConstants.setBold(messageStyle, true);
            }

            // Append timestamp
            doc.insertString(doc.getLength(), "[" + timestamp + "] ", timeStyle);

            // Append message
            doc.insertString(doc.getLength(), message + "\n", messageStyle);

        } catch (BadLocationException e) {
            // Fallback to plain text
            chatArea.setText(
                    chatArea.getText() + "[" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"))
                            + "] " + message
                            + "\n");
        }
    }

    private void updateConnectionStatus(boolean connected, String details) {
        isConnected = connected;
        if (connected) {
            connectionLabel.setText("🟢 Connected to " + details);
            connectionLabel.setForeground(SUCCESS_COLOR);
            disconnectButton.setEnabled(true);
        } else {
            connectionLabel.setText("🔴 Disconnected");
            connectionLabel.setForeground(DANGER_COLOR);
            disconnectButton.setEnabled(false);
        }
    }

    private void updateStatus(String status, Color color) {
        statusLabel.setText(status);
        statusLabel.setForeground(color);
    }

    private void disconnect() {
        if (isConnected && out != null) {
            out.println("/quit");
            isConnected = false;
            updateConnectionStatus(false, "Manually disconnected");
            updateStatus("🔌 Disconnected", SECONDARY_COLOR);
            appendToChat("You have disconnected from the server", "SYSTEM", SECONDARY_COLOR);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        sendMessage();
    }

    private void sendMessage() {
        String message = messageField.getText().trim();
        if (!message.isEmpty() && isConnected && out != null) {
            // Show user's message locally with timestamp
            appendToChat("➤ " + message, "USER", new Color(25, 135, 84));

            // Send to server
            out.println(message);
            messageField.setText("");

            // Update status
            updateStatus("💬 Message sent", SUCCESS_COLOR);
        } else if (message.isEmpty()) {
            updateStatus("⚠️ Please type a message", SECONDARY_COLOR);
        } else if (!isConnected) {
            updateStatus("❌ Not connected to server", DANGER_COLOR);
        }
    }

    public static void main(String[] args) {
        // Set system look and feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            // Use default look and feel
        }

        SwingUtilities.invokeLater(() -> {
            try {
                showConnectionDialog();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(
                        null, "Error starting client: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    private static void showConnectionDialog() {
        // Modern connection dialog
        JDialog dialog = new JDialog((Frame) null, "🎮 Connect to Words Crush Game Server", true);
        dialog.setLayout(new BorderLayout());

        // Main panel
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(new Color(248, 249, 250));

        GridBagConstraints gbc = new GridBagConstraints();

        // Title
        JLabel titleLabel = new JLabel("🎮 Words Crush Game Client");
        titleLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 18));
        titleLabel.setForeground(new Color(51, 122, 183));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(0, 0, 20, 0);
        mainPanel.add(titleLabel, gbc);

        // Server address field
        gbc.gridwidth = 1;
        gbc.insets = new Insets(5, 0, 5, 10);
        gbc.gridx = 0;
        gbc.gridy = 1;
        mainPanel.add(new JLabel("Server Address:"), gbc);

        JTextField addressField = new JTextField("127.0.0.1", 15);
        addressField.setPreferredSize(new Dimension(200, 30));
        gbc.gridx = 1;
        gbc.gridy = 1;
        mainPanel.add(addressField, gbc);

        // Port field
        gbc.gridx = 0;
        gbc.gridy = 2;
        mainPanel.add(new JLabel("Port:"), gbc);

        JTextField portField = new JTextField("8001", 15);
        portField.setPreferredSize(new Dimension(200, 30));
        gbc.gridx = 1;
        gbc.gridy = 2;
        mainPanel.add(portField, gbc);

        // Buttons panel
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setBackground(new Color(248, 249, 250));

        JButton connectButton = new JButton("🔗 Connect");
        connectButton.setBackground(new Color(40, 167, 69));
        connectButton.setForeground(Color.WHITE);
        connectButton.setFocusPainted(false);

        JButton cancelButton = new JButton("❌ Cancel");
        cancelButton.setBackground(new Color(220, 53, 69));
        cancelButton.setForeground(Color.WHITE);
        cancelButton.setFocusPainted(false);

        buttonPanel.add(connectButton);
        buttonPanel.add(cancelButton);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(20, 0, 0, 0);
        mainPanel.add(buttonPanel, gbc);

        dialog.add(mainPanel, BorderLayout.CENTER);

        // Event handlers
        connectButton.addActionListener(e -> {
            try {
                String serverAddress = addressField.getText().trim();
                int serverPort = Integer.parseInt(portField.getText().trim());

                if (serverAddress.isEmpty()) {
                    JOptionPane.showMessageDialog(
                            dialog, "Please enter a server address", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                dialog.dispose();

                // Create and show the client window
                Client client = new Client(serverAddress, serverPort);
                client.pack();
                client.setLocationRelativeTo(null);
                client.setVisible(true);

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(
                        dialog, "Please enter a valid port number", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(
                        dialog, "Failed to connect: " + ex.getMessage(), "Connection Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        cancelButton.addActionListener(e -> {
            dialog.dispose();
            System.exit(0);
        });

        // Enter key support
        ActionListener connectAction = connectButton.getActionListeners()[0];
        addressField.addActionListener(connectAction);
        portField.addActionListener(connectAction);

        dialog.pack();
        dialog.setLocationRelativeTo(null);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.setVisible(true);
    }
}
