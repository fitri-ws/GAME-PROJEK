
public class GameLauncher {
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> {
            new MainMenu().setVisible(true);
        });
    }
}