package game.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import game.logic.Game;
import game.model.EasyLevel;
import game.model.HardLevel;
import game.model.Level;
import game.model.MediumLevel;

public class GameGUI {
    private JFrame frame;
    private JTextField inputField;
    private JButton guessButton;
    private JButton retryButton;
    private JButton newGameButton;
    private JButton exitButton;
    private JComboBox<String> levelBox;
    private JLabel hintLabel;
    private JLabel statusLabel;
    private JLabel attemptsLabel;
    private JLabel titleLabel;
    private Game game;

    public GameGUI() {
        game = new Game(new EasyLevel());
        buildInterface();
        registerEvents();
    }

    public void buildInterface() {
        frame = new JFrame("Number Guessing Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(760, 460);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout(12, 12));

        titleLabel = new JLabel("Multilevel Number Guessing Game", SwingConstants.CENTER);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 22));
        titleLabel.setForeground(new Color(25, 60, 120));

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(new Color(235, 244, 255));
        topPanel.add(titleLabel, BorderLayout.CENTER);

        JPanel centerPanel = new JPanel(new GridLayout(5, 2, 10, 10));
        centerPanel.setBackground(new Color(248, 250, 252));

        levelBox = new JComboBox<>(new String[] { "Easy", "Medium", "Hard" });
        inputField = new JTextField();
        guessButton = new JButton("Guess ?");
        retryButton = new JButton("Retry <");
        newGameButton = new JButton("New Game *");
        exitButton = new JButton("Exit X");
        hintLabel = new JLabel("Choose a level and guess a number between 1 and 100.");
        statusLabel = new JLabel("Game started.");
        attemptsLabel = new JLabel();

        centerPanel.add(new JLabel("Difficulty Level:"));
        centerPanel.add(levelBox);
        centerPanel.add(new JLabel("Enter Guess:"));
        centerPanel.add(inputField);
        centerPanel.add(new JLabel("Hint:"));
        centerPanel.add(hintLabel);
        centerPanel.add(new JLabel("Status:"));
        centerPanel.add(statusLabel);
        centerPanel.add(new JLabel("Attempts:"));
        centerPanel.add(attemptsLabel);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(new Color(235, 244, 255));
        bottomPanel.add(guessButton);
        bottomPanel.add(retryButton);
        bottomPanel.add(newGameButton);
        bottomPanel.add(exitButton);

        frame.add(topPanel, BorderLayout.NORTH);
        frame.add(centerPanel, BorderLayout.CENTER);
        frame.add(bottomPanel, BorderLayout.SOUTH);

        updateStatus();
    }

    public void registerEvents() {
        guessButton.addActionListener(event -> handleGuess());
        retryButton.addActionListener(event -> {
            game.resetGame();
            inputField.setText("");
            inputField.setEditable(true);
            guessButton.setEnabled(true);
            hintLabel.setText("Game reset. Try again.");
            statusLabel.setText("Retry started.");
            updateStatus();
        });
        newGameButton.addActionListener(event -> {
            game.setLevel(createLevel((String) levelBox.getSelectedItem()));
            game.startNewGame();
            inputField.setText("");
            inputField.setEditable(true);
            guessButton.setEnabled(true);
            hintLabel.setText("New game started. Enter a guess from 1 to 100.");
            statusLabel.setText("Fresh game ready.");
            updateStatus();
        });
        exitButton.addActionListener(event -> frame.dispose());
        levelBox.addActionListener(event -> {
            game.setLevel(createLevel((String) levelBox.getSelectedItem()));
            game.startNewGame();
            inputField.setText("");
            inputField.setEditable(true);
            guessButton.setEnabled(true);
            hintLabel.setText("Level changed to " + levelBox.getSelectedItem() + ".");
            statusLabel.setText("A new level is active.");
            updateStatus();
        });
    }

    public void displayHint(String message) {
        hintLabel.setText(message);
    }

    public void updateStatus() {
        attemptsLabel.setText(game.getGuessCount() + " used / " + game.getMaxGuesses() + " total. Left: " + game.getRemainingGuesses());
    }

    public void showWinnerDialog(String message) {
        JOptionPane.showMessageDialog(frame, message, "Winner", JOptionPane.INFORMATION_MESSAGE);
    }

    public void showLoserDialog(String message) {
        JOptionPane.showMessageDialog(frame, message, "Game Over", JOptionPane.WARNING_MESSAGE);
    }

    public void show() {
        frame.setVisible(true);
    }

    private void handleGuess() {
        String input = inputField.getText().trim();

        if (input.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Please enter a number.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int guess;

        try {
            guess = Integer.parseInt(input);
        } catch (NumberFormatException exception) {
            JOptionPane.showMessageDialog(frame, "Enter digits only.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (guess < Game.MIN_NUMBER || guess > Game.MAX_NUMBER) {
            JOptionPane.showMessageDialog(
                frame,
                "Enter a number between " + Game.MIN_NUMBER + " and " + Game.MAX_NUMBER + ".",
                "Out of Range",
                JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        boolean winner = game.isWinningGuess(guess);
        String result = game.checkGuess(guess);
        displayHint(result);
        updateStatus();

        String warning = game.getWarningMessage();
        if (!warning.isEmpty()) {
            statusLabel.setText(warning);
            statusLabel.setForeground(new Color(180, 90, 0));
        } else {
            statusLabel.setForeground(new Color(20, 20, 20));
            statusLabel.setText(game.isGameOver() ? "Game finished." : "Keep trying.");
        }

        if (winner) {
            inputField.setEditable(false);
            guessButton.setEnabled(false);
            statusLabel.setForeground(new Color(0, 128, 0));
            statusLabel.setText("You won the game.");
            showWinnerDialog(result);
        } else if (game.isGameOver()) {
            inputField.setEditable(false);
            guessButton.setEnabled(false);
            statusLabel.setForeground(Color.RED);
            statusLabel.setText("You lost the game.");
            showLoserDialog(result);
        }

        inputField.setText("");
    }

    private Level createLevel(String levelName) {
        if ("Medium".equalsIgnoreCase(levelName)) {
            return new MediumLevel();
        }

        if ("Hard".equalsIgnoreCase(levelName)) {
            return new HardLevel();
        }

        return new EasyLevel();
    }
}
