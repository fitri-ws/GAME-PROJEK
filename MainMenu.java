import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MainMenu extends JFrame {
    private JComboBox<String> sizeSelector;
    private JComboBox<String> modeSelector;
    private JButton btnStart, btnSettings;

    public MainMenu() {
        super("Menu Utama Tic Tac Toe");
        setSize(400, 250);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());
        getContentPane().setBackground(new Color(54, 57, 63));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10,10,10,10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblSize = new JLabel("Pilih ukuran papan:");
        lblSize.setForeground(Color.WHITE);
        gbc.gridx = 0; gbc.gridy = 0;
        add(lblSize, gbc);

        sizeSelector = new JComboBox<>();
        for (int i = 3; i <= 30; i++) {
            sizeSelector.addItem(i + " x " + i);
        }
        gbc.gridx = 1; gbc.gridy = 0;
        add(sizeSelector, gbc);

        JLabel lblMode = new JLabel("Pilih mode permainan:");
        lblMode.setForeground(Color.WHITE);
        gbc.gridx = 0; gbc.gridy = 1;
        add(lblMode, gbc);

        modeSelector = new JComboBox<>(new String[]{"2 Pemain", "1 Pemain (AI)"});
        gbc.gridx = 1; gbc.gridy = 1;
        add(modeSelector, gbc);

        btnStart = new JButton("Mulai");
        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2;
        add(btnStart, gbc);

        btnSettings = new JButton("Pengaturan");
        gbc.gridy = 3;
        add(btnSettings, gbc);

        btnStart.addActionListener(e -> startGame());
        btnSettings.addActionListener(e -> openSettings());

    }

    private void startGame() {
        int size = sizeSelector.getSelectedIndex() + 3;
        boolean isSinglePlayer = modeSelector.getSelectedIndex() == 1;

        TicTacToeGame game = new TicTacToeGame();
        game.setBoardSize(size);
        game.setSinglePlayerMode(isSinglePlayer);
        game.resetGame();

        game.setVisible(true);
        this.dispose();
    }

    private void openSettings() {
        JOptionPane.showMessageDialog(this, "Fitur pengaturan belum tersedia.");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainMenu menu = new MainMenu();
            menu.setVisible(true);
        });
    }
}