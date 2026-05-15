package game.model;

public class MediumLevel extends Level {
    public MediumLevel() {
        super("Medium", 7);
    }

    @Override
    public String getHintStyle() {
        return "Moderate";
    }
}
