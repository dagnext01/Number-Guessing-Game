package game.logic;

public class AwardSystem {
    public String getAwardMessage(int guessCount) {
        if (guessCount <= 1) {
            return "Excellent!! You won on the first guess.";
        }

        if (guessCount == 2) {
            return "Very Good!! You won on the second guess.";
        }

        if (guessCount == 3) {
            return "Good!! You won on the third guess.";
        }

        if (guessCount == 4) {
            return "Satisfactory. Nice effort.";
        }

        return "Nice. You got the number.";
    }
}
