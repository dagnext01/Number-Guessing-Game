package game.model;

public class Player {
    private String playerName;
    private int wins;
    private int losses;
    private int score;

    public Player() {
    }

    public Player(String playerName) {
        this.playerName = playerName;
    }

    public void addWin() {
        wins++;
    }

    public void addLoss() {
        losses++;
    }

    public void updateScore() {
        score++;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public int getWins() {
        return wins;
    }

    public int getLosses() {
        return losses;
    }

    public int getScore() {
        return score;
    }
}
