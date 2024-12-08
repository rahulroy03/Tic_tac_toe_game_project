package Project;

import java.util.Scanner;

public class TicTacToe {

    public static char[][] board = new char[3][3];
    public static final char PLAYER = 'X';
    public static final char COMPUTER = 'O';
    public static final char EMPTY = ' ';

    public static void main(String[] args) {
        initializeBoard();
        printBoard();
        while (true) {
            playerMove();
            if (checkWinner(PLAYER)) {
                printBoard();
                System.out.println("Player wins!");
                break;
            }
            if (isBoardFull()) {
                printBoard();
                System.out.println("It's a draw!");
                break;
            }
            printBoard();
            computerMove();
            if (checkWinner(COMPUTER)) {
                printBoard();
                System.out.println("Computer wins!");
                break;
            }
            if (isBoardFull()) {
                printBoard();
                System.out.println("It's a draw!");
                break;
            }
            printBoard();
        }
    }

    // Initialize the game board with empty spaces
    public static void initializeBoard() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = EMPTY;
            }
        }
    }

    // Print the board
    public static void printBoard() {
        System.out.println("-------------");
        for (int i = 0; i < 3; i++) {
            System.out.print("| ");
            for (int j = 0; j < 3; j++) {
                System.out.print(board[i][j] + " | ");
            }
            System.out.println();
            System.out.println("-------------");
        }
    }

    // Get the player's move
    public static void playerMove() {
        Scanner scanner = new Scanner(System.in);
        int row, col;
        while (true) {
            System.out.println("Enter your move (row and column): ");
            row = scanner.nextInt();
            col = scanner.nextInt();
            if (row >= 0 && row < 3 && col >= 0 && col < 3 && board[row][col] == EMPTY) {
                board[row][col] = PLAYER;
                break;
            } else {
                System.out.println("Invalid move. Try again.");
            }
        }
    }

    // Check if the current player has won
    public static boolean checkWinner(char player) {
        // Check rows, columns, and diagonals
        for (int i = 0; i < 3; i++) {
            if (board[i][0] == player && board[i][1] == player && board[i][2] == player) {
                return true;
            }
            if (board[0][i] == player && board[1][i] == player && board[2][i] == player) {
                return true;
            }
        }
        if (board[0][0] == player && board[1][1] == player && board[2][2] == player) {
            return true;
        }
        if (board[0][2] == player && board[1][1] == player && board[2][0] == player) {
            return true;
        }
        return false;
    }

    // Check if the board is full
    public static boolean isBoardFull() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == EMPTY) {
                    return false;
                }
            }
        }
        return true;
    }

    // Computer move (using Minimax algorithm)
    public static void computerMove() {
        int bestValue = Integer.MIN_VALUE;
        int bestRow = -1;
        int bestCol = -1;

        // Try every empty spot to find the best move
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == EMPTY) {
                    board[i][j] = COMPUTER;
                    int moveValue = minimax(board, 0, false);
                    board[i][j] = EMPTY;

                    if (moveValue > bestValue) {
                        bestRow = i;
                        bestCol = j;
                        bestValue = moveValue;
                    }
                }
            }
        }

        board[bestRow][bestCol] = COMPUTER;
        System.out.println("Computer chose: " + bestRow + " " + bestCol);
    }

    // Minimax algorithm to find the best move
    public static int minimax(char[][] board, int depth, boolean isMax) {
        if (checkWinner(COMPUTER)) {
            return 10 - depth;
        }
        if (checkWinner(PLAYER)) {
            return depth - 10;
        }
        if (isBoardFull()) {
            return 0;
        }

        if (isMax) {
            int best = Integer.MIN_VALUE;

            // Maximize for computer
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (board[i][j] == EMPTY) {
                        board[i][j] = COMPUTER;
                        best = Math.max(best, minimax(board, depth + 1, false));
                        board[i][j] = EMPTY;
                    }
                }
            }
            return best;
        } else {
            int best = Integer.MAX_VALUE;

            // Minimize for player
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (board[i][j] == EMPTY) {
                        board[i][j] = PLAYER;
                        best = Math.min(best, minimax(board, depth + 1, true));
                        board[i][j] = EMPTY;
                    }
                }
            }
            return best;
        }
    }
}
