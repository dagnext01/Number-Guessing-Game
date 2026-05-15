package game.model;

public class Level {
    private String levelName;
    private int maxAttempts;

    public Level() {
    }

    public Level(String levelName, int maxAttempts) {
        this.levelName = levelName;
        this.maxAttempts = maxAttempts;
    }

    public String getLevelName() {
        return levelName;
    }

    public void setLevelName(String levelName) {
        this.levelName = levelName;
    }

    public int getMaxAttempts() {
        return maxAttempts;
    }

    public void setMaxAttempts(int maxAttempts) {
        this.maxAttempts = maxAttempts;
    }

    public String getHintStyle() {
        return "Standard";
    }
}
