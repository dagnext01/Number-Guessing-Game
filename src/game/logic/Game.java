package game.logic;

import java.util.Random;

import game.model.Level;
import game.model.Player;

public class Game {
    public static final int MIN_NUMBER = 1;
    public static final int MAX_NUMBER = 100;

    private int secretNumber;
    private int guessCount;
    private int maxGuesses;
    private boolean gameOver;
    private Level currentLevel;
    private Player player;
    private final HintGenerator hintGenerator;
    private final AwardSystem awardSystem;
    private final Random random;

    public Game(Level level) {
        this.currentLevel = level;
        this.hintGenerator = new HintGenerator();
        this.awardSystem = new AwardSystem();
        this.random = new Random();
        startNewGame();
    }

    public void startNewGame() {
        guessCount = 0;
        gameOver = false;
        maxGuesses = currentLevel.getMaxAttempts();
        generateSecretNumber();
    }

    public void generateSecretNumber() {
        secretNumber = random.nextInt(MAX_NUMBER) + MIN_NUMBER;
    }

    public String checkGuess(int guess) {
        if (gameOver) {
            return "The game is already over. Start a new game to continue.";
        }

        guessCount++;

        if (guess == secretNumber) {
            gameOver = true;
            if (player != null) {
                player.addWin();
                player.updateScore();
            }
            return "Correct! " + awardSystem.getAwardMessage(guessCount);
        }

        String directionHint = hintGenerator.getDirectionHint(guess, secretNumber);
        String intervalHint = hintGenerator.getIntervalHint(guess, secretNumber, currentLevel);

        if (guessCount >= maxGuesses) {
            gameOver = true;
            if (player != null) {
                player.addLoss();
            }
            return directionHint + " No guesses left. You lost the game. The number was " + secretNumber + ".";
        }

        if (intervalHint == null || intervalHint.isBlank()) {
            return directionHint;
        }

        return directionHint + " " + intervalHint;
    }

    public int getRemainingGuesses() {
        return Math.max(0, maxGuesses - guessCount);
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public void resetGame() {
        startNewGame();
    }

    public void setLevel(Level level) {
        this.currentLevel = level;
        this.maxGuesses = level.getMaxAttempts();
    }

    public boolean isWinningGuess(int guess) {
        return guess == secretNumber;
    }

    public String getWarningMessage() {
        int remainingGuesses = getRemainingGuesses();

        if (gameOver) {
            return "";
        }

        if (remainingGuesses == 1) {
            return "Warning: only 1 guess left.";
        }

        if (remainingGuesses <= 3) {
            return "Warning: only " + remainingGuesses + " guesses left.";
        }

        return "";
    }

    public int getSecretNumber() {
        return secretNumber;
    }

    public int getGuessCount() {
        return guessCount;
    }

    public int getMaxGuesses() {
        return maxGuesses;
    }

    public Level getCurrentLevel() {
        return currentLevel;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}
