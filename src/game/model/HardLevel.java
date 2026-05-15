package game.model;

public class HardLevel extends Level {
    public HardLevel() {
        super("Hard", 5);
    }

    @Override
    public String getHintStyle() {
        return "Minimal";
    }
}
