package net.anotheria.strel.basic.knightstour;

/**
 * This class is responsible for printing chessboard to the console.
 * It uses tabs to layout content, it's not good. In next version
 * it will use Java formatting tools to layout table in console.
 *
 * @author Strel97
 * @version 1.%I%
 */
public class ChessboardPrinter {
    /**
     * Receives two dimensional array as argument and outputs it
     * to the console as chessboard.
     *
     * @param board Array to be represented as chessboard
     */
    public static void printBoard(int[][] board) {
        printHeader(board.length);
        printDelimiter(board.length);
        System.out.println();

        for (int i = 0; i < board.length; i++) {
            System.out.print((board.length - i) + ": ");
            for (int j = 0; j < board[i].length; j++) {
                System.out.print("|\t" + board[i][j] + "\t|");
            }

            printDelimiter(board[i].length);
            System.out.println();
        }
        System.out.println();
    }

    /**
     * Prints chessboard line delimiter of the length given in parameter.
     * @param length    The length of delimiter
     */
    private static void printDelimiter(int length) {
        System.out.println();
        System.out.print("\t");
        for (int i = 0; i < length; i++) {
            System.out.print(" \t=\t ");
        }
    }

    /**
     * Prints chessboard header line of the length given in parameter.
     * Chessboard header is horizontal line, that indicates columns
     * of chessboard using Latin characters (A, B, C...).
     *
     * @param length    The length of header
     */
    private static void printHeader(int length) {
        System.out.println();
        System.out.print("\t");
        for (int i = 0; i < length; i++) {
            System.out.print(" \t" + (char) (i + 65) + "\t");
        }
    }
}
