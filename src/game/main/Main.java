package game.main;

import javax.swing.SwingUtilities;

import game.gui.GameGUI;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GameGUI gui = new GameGUI();
            gui.show();
        });
    }
}
