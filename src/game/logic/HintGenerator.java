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

        if ("minimal".equals(style)) {
            return "";
        }

        if ("detailed".equals(style)) {
            if (difference <= 3) {
                return "You are extremely close.";
            }

            if (difference <= 8) {
                return "You are very close.";
            }

            if (difference <= 15) {
                return "You are close.";
            }

            return "You are still far from the number.";
        }

        if (difference <= 5) {
            return "You are close.";
        }

        return "You are not very close yet.";
    }
}
