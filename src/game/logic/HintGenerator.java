package game.logic;

import game.model.Level;

public class HintGenerator {
    public String getDirectionHint(int guess, int secret) {
        if (guess < secret) {
            return "Too low.";
        }

        if (guess > secret) {
            return "Too high.";
        }

        return "Correct guess.";
    }

    public String getIntervalHint(int guess, int secret, Level level) {
        int difference = Math.abs(secret - guess);
        String style = level.getHintStyle().toLowerCase();
        int window;

        if ("detailed".equals(style)) {
            if (difference <= 5) {
                window = 8;
            } else if (difference <= 15) {
                window = 15;
            } else {
                window = 25;
            }
        } else if ("moderate".equals(style)) {
            if (difference <= 8) {
                window = 10;
            } else if (difference <= 18) {
                window = 18;
            } else {
                window = 30;
            }
        } else {
            if (difference <= 10) {
                window = 12;
            } else if (difference <= 20) {
                window = 20;
            } else {
                window = 35;
            }
        }

        int lowerBound = Math.max(Game.MIN_NUMBER, secret - window);
        int upperBound = Math.min(Game.MAX_NUMBER, secret + window);
        return "Try focusing between " + lowerBound + " and " + upperBound + ".";
    }
}
