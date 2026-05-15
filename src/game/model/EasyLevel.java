package game.model;

public class EasyLevel extends Level {
    public EasyLevel() {
        super("Easy", 10);
    }

    @Override
    public String getHintStyle() {
        return "Detailed";
    }
}
