import java.util.*;

public class TicTacToeAI {
    private char[][] board;
    private int size;
    private char aiPlayer;
    private char humanPlayer;

    public TicTacToeAI(char[][] board, char aiPlayer, char humanPlayer) {
        this.board = board;
        this.size = board.length;
        this.aiPlayer = aiPlayer;
        this.humanPlayer = humanPlayer;
    }

    // AI pilih langkah terbaik (saat ini acak dari sel kosong)
    public int[] getBestMove() {
        List<int[]> emptyCells = new ArrayList<>();
        for (int r = 0; r < size; r++) {
            for (int c = 0; c < size; c++) {
                if (board[r][c] == '\0') {
                    emptyCells.add(new int[]{r, c});
                }
            }
        }
        if (emptyCells.isEmpty()) return null;

        // Bisa modifikasi jadi AI lebih pintar (Minimax dll)
        Random rand = new Random();
        return emptyCells.get(rand.nextInt(emptyCells.size()));
    }
}