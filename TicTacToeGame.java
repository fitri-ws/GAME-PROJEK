import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class TicTacToeGame extends JFrame {
    private int boardSize = 20;
    private boolean isSinglePlayer = false;
    private JButton[][] buttons;
    private char[][] board;
    private boolean xTurn = true;
    private boolean gameEnded = false;
    private JPanel boardPanel;
    private JLabel lblStatus, lblScoreX, lblScoreO;
    private int scoreX = 0, scoreO = 0;
    private javax.swing.Timer gameTimer;
    private int timeLeft; // in seconds
    private JLabel lblTimer;
    private JButton btnReset, btnPause;
    private boolean paused = false;

    private final Color COLOR_BG = new Color(54, 57, 63);
    private final Color COLOR_X = new Color(0, 255, 0);
    private final Color COLOR_O = new Color(255, 0, 0);
    private final Color COLOR_LINE_X = new Color(0, 200, 0);
    private final Color COLOR_LINE_O = new Color(200, 0, 0);

    private final int MAX_TIME_SECONDS = 40 * 60; // 40 minutes max

    public TicTacToeGame() {
        setTitle("Tic Tac Toe");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(800, 850);
        setLocationRelativeTo(null);
        getContentPane().setBackground(COLOR_BG);
        setLayout(new BorderLayout());

        lblStatus = new JLabel("Giliran: X");
        lblStatus.setForeground(Color.WHITE);
        lblStatus.setFont(new Font("Arial", Font.BOLD, 16));
        lblStatus.setHorizontalAlignment(SwingConstants.CENTER);

        lblTimer = new JLabel();
        lblTimer.setForeground(Color.WHITE);
        lblTimer.setFont(new Font("Arial", Font.BOLD, 14));
        lblTimer.setHorizontalAlignment(SwingConstants.CENTER);

        lblScoreX = new JLabel("Skor X: 0");
        lblScoreX.setForeground(COLOR_X);
        lblScoreX.setFont(new Font("Arial", Font.BOLD, 14));
        lblScoreO = new JLabel("Skor O: 0");
        lblScoreO.setForeground(COLOR_O);
        lblScoreO.setFont(new Font("Arial", Font.BOLD, 14));

        JPanel topPanel = new JPanel(new GridLayout(2, 2));
        topPanel.setBackground(COLOR_BG);
        topPanel.add(lblStatus);
        topPanel.add(lblTimer);
        topPanel.add(lblScoreX);
        topPanel.add(lblScoreO);

        add(topPanel, BorderLayout.NORTH);

        boardPanel = new JPanel();
        add(boardPanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(COLOR_BG);
        btnReset = new JButton("Reset");
        btnPause = new JButton("Jeda");
        bottomPanel.add(btnPause);
        bottomPanel.add(btnReset);
        add(bottomPanel, BorderLayout.SOUTH);

        btnReset.addActionListener(e -> resetGame());
        btnPause.addActionListener(e -> togglePause());

        initGame();
    }

    public void setBoardSize(int size) {
        this.boardSize = size;
    }

    public void setSinglePlayerMode(boolean single) {
        this.isSinglePlayer = single;
    }

    public void resetGame() {
        if (gameTimer != null) {
            gameTimer.stop();
        }
        xTurn = true;
        gameEnded = false;
        board = new char[boardSize][boardSize];
        timeLeft = Math.min(boardSize * 60, MAX_TIME_SECONDS);
        lblTimer.setText("Waktu tersisa: " + formatTime(timeLeft));
        lblStatus.setText("Giliran: X");
        paused = false;
        btnPause.setText("Jeda");
        buildBoard();
        startTimer();
    }

    private void initGame() {
        board = new char[boardSize][boardSize];
        buttons = new JButton[boardSize][boardSize];
        buildBoard();
        timeLeft = Math.min(boardSize * 60, MAX_TIME_SECONDS);
        lblTimer.setText("Waktu tersisa: " + formatTime(timeLeft));
        startTimer();
    }

    private void buildBoard() {
        boardPanel.removeAll();
        boardPanel.setLayout(new GridLayout(boardSize, boardSize));
        for (int r = 0; r < boardSize; r++) {
            for (int c = 0; c < boardSize; c++) {
                JButton btn = new JButton("");
                btn.setFont(new Font("Arial", Font.BOLD, Math.max(10, 600 / boardSize)));
                btn.setBackground(Color.WHITE);
                btn.setFocusPainted(false);
                final int row = r;
                final int col = c;
                btn.addActionListener(e -> handleClick(row, col));
                buttons[r][c] = btn;
                boardPanel.add(btn);
            }
        }
        boardPanel.revalidate();
        boardPanel.repaint();
    }

    private void handleClick(int row, int col) {
        if (gameEnded || paused) return;
        if (board[row][col] != '\0') return;

        board[row][col] = xTurn ? 'X' : 'O';
        buttons[row][col].setText(String.valueOf(board[row][col]));
        buttons[row][col].setForeground(xTurn ? COLOR_X : COLOR_O);

        if (checkWin(row, col)) {
            gameEnded = true;
            if (xTurn) {
                scoreX++;
                lblScoreX.setText("Skor X: " + scoreX);
            } else {
                scoreO++;
                lblScoreO.setText("Skor O: " + scoreO);
            }
            lblStatus.setText("Pemenang: " + (xTurn ? "X" : "O"));
            highlightWin(row, col);
            if (gameTimer != null) gameTimer.stop();
            return;
        } else if (isBoardFull()) {
            gameEnded = true;
            lblStatus.setText("Seri!");
            if (gameTimer != null) gameTimer.stop();
            return;
        }

        xTurn = !xTurn;
        lblStatus.setText("Giliran: " + (xTurn ? "X" : "O"));

        if (isSinglePlayer && !xTurn && !gameEnded) {
            makeAIMove();
        }
    }

    private void makeAIMove() {
        // AI sederhana: pilih acak dari yang kosong
        java.util.List<int[]> emptyCells = new ArrayList<>();
        for (int r = 0; r < boardSize; r++) {
            for (int c = 0; c < boardSize; c++) {
                if (board[r][c] == '\0') emptyCells.add(new int[]{r,c});
            }
        }
        if (emptyCells.isEmpty()) return;
        int[] move = emptyCells.get(new Random().nextInt(emptyCells.size()));

        // Delay AI move sedikit agar natural
        javax.swing.Timer aiTimer = new javax.swing.Timer(500, e -> {
            handleClick(move[0], move[1]);
        });
        aiTimer.setRepeats(false);
        aiTimer.start();
    }

    private boolean isBoardFull() {
        for (int r = 0; r < boardSize; r++)
            for (int c = 0; c < boardSize; c++)
                if (board[r][c] == '\0') return false;
        return true;
    }

    private boolean checkWin(int row, int col) {
        char player = board[row][col];
        if (player == '\0') return false;

        // Periksa 4 arah (horizontal, vertikal, diagonal, anti-diagonal)
        return (countConsecutive(row, col, 0, 1, player) + countConsecutive(row, col, 0, -1, player) - 1 >= 3) ||
               (countConsecutive(row, col, 1, 0, player) + countConsecutive(row, col, -1, 0, player) - 1 >= 3) ||
               (countConsecutive(row, col, 1, 1, player) + countConsecutive(row, col, -1, -1, player) - 1 >= 3) ||
               (countConsecutive(row, col, 1, -1, player) + countConsecutive(row, col, -1, 1, player) - 1 >= 3);
    }

    // Hitung jumlah tanda player berturut-turut di arah (dr, dc)
    private int countConsecutive(int r, int c, int dr, int dc, char player) {
        int count = 0;
        int nr = r, nc = c;
        while (nr >= 0 && nr < boardSize && nc >= 0 && nc < boardSize && board[nr][nc] == player) {
            count++;
            nr += dr;
            nc += dc;
        }
        return count;
    }

    private void highlightWin(int row, int col) {
        char player = board[row][col];
        // cari dan beri warna garis sesuai arah kemenangan (garis tetap sama warna tapi tiap pemain beda)
        // cek 4 arah, jika >=3 berturut2 beri garis

        // Kita buat garis horizontal, vertikal, diagonal, anti-diagonal sebagai panel terpisah dengan warna garis

        // Hapus highlight sebelumnya
        for (int r = 0; r < boardSize; r++)
            for (int c = 0; c < boardSize; c++)
                buttons[r][c].setBorder(UIManager.getBorder("Button.border"));

        // Cek dan highlight setiap arah jika menang
        if (countConsecutive(row, col, 0, 1, player) + countConsecutive(row, col, 0, -1, player) - 1 >= 3) {
            highlightDirection(row, col, 0, 1, player);
            highlightDirection(row, col, 0, -1, player);
        }
        if (countConsecutive(row, col, 1, 0, player) + countConsecutive(row, col, -1, 0, player) - 1 >= 3) {
            highlightDirection(row, col, 1, 0, player);
            highlightDirection(row, col, -1, 0, player);
        }
        if (countConsecutive(row, col, 1, 1, player) + countConsecutive(row, col, -1, -1, player) - 1 >= 3) {
            highlightDirection(row, col, 1, 1, player);
            highlightDirection(row, col, -1, -1, player);
        }
        if (countConsecutive(row, col, 1, -1, player) + countConsecutive(row, col, -1, 1, player) - 1 >= 3) {
            highlightDirection(row, col, 1, -1, player);
            highlightDirection(row, col, -1, 1, player);
        }
    }

    private void highlightDirection(int r, int c, int dr, int dc, char player) {
        int nr = r, nc = c;
        Color color = player == 'X' ? COLOR_LINE_X : COLOR_LINE_O;

        while (nr >= 0 && nr < boardSize && nc >= 0 && nc < boardSize && board[nr][nc] == player) {
            buttons[nr][nc].setBorder(BorderFactory.createLineBorder(color, 3));
            nr += dr;
            nc += dc;
        }
    }

    private void startTimer() {
        if (gameTimer != null) {
            gameTimer.stop();
        }
        gameTimer = new javax.swing.Timer(1000, e -> {
            if (!paused && !gameEnded) {
                timeLeft--;
                lblTimer.setText("Waktu tersisa: " + formatTime(timeLeft));
                if (timeLeft <= 0) {
                    gameEnded = true;
                    lblStatus.setText("Waktu habis! Permainan selesai.");
                    gameTimer.stop();
                }
            }
        });
        gameTimer.start();
    }

    private void togglePause() {
        if (gameEnded) return;
        paused = !paused;
        btnPause.setText(paused ? "Lanjut" : "Jeda");
        lblStatus.setText(paused ? "Permainan Dijeda" : "Giliran: " + (xTurn ? "X" : "O"));
    }

    private String formatTime(int seconds) {
        int m = seconds / 60;
        int s = seconds % 60;
        return String.format("%02d:%02d", m, s);
    }

    private void enableBoard() {
        for (int r = 0; r < boardSize; r++)
            for (int c = 0; c < boardSize; c++)
                buttons[r][c].setEnabled(true);
    }
}