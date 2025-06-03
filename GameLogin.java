import javax.swing.*;hari_selasa
import java.awt.*;
import java.awt.event.*;

public class GameLogin extends JFrame {
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JButton btnLogin;

    public GameLogin() {
        super("Login Tic Tac Toe");
        setSize(350, 200);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());
        getContentPane().setBackground(new Color(54, 57, 63));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8,8,8,8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblUser = new JLabel("Username:");
        lblUser.setForeground(Color.WHITE);
        gbc.gridx = 0; gbc.gridy = 0;
        add(lblUser, gbc);

        txtUsername = new JTextField(15);
        gbc.gridx = 1; gbc.gridy = 0;
        add(txtUsername, gbc);

        JLabel lblPass = new JLabel("Password:");
        lblPass.setForeground(Color.WHITE);
        gbc.gridx = 0; gbc.gridy = 1;
        add(lblPass, gbc);

        txtPassword = new JPasswordField(15);
        gbc.gridx = 1; gbc.gridy = 1;
        add(txtPassword, gbc);

        btnLogin = new JButton("Login");
        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2;
        add(btnLogin, gbc);

        btnLogin.addActionListener(e -> attemptLogin());
    }

    private void attemptLogin() {
        String user = txtUsername.getText();
        String pass = new String(txtPassword.getPassword());

        // Contoh validasi sederhana (user: admin, pass: admin)
        if(user.equals("admin") && pass.equals("admin")) {
            JOptionPane.showMessageDialog(this, "Login berhasil!");
            MainMenu mainMenu = new MainMenu();
            mainMenu.setVisible(true);
            this.dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Username atau password salah!");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GameLogin login = new GameLogin();
            login.setVisible(true);
        });
    }
}
