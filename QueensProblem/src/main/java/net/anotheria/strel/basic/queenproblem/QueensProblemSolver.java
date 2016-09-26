package net.anotheria.strel.basic.queenproblem;

import java.util.ArrayList;
import java.util.List;

/**
 * Finds all solutions for Queens Problem for chessboard
 * of given dimensions using simple bust.
 *
 * @author Strel97
 */
public class QueensProblemSolver {

    /**
     * Contains queens positions in form (index -> number of line; value -> number of column)
     */
    private int[] queens;

    /**
     * Stores all possible solutions for queens problem
     */
    private List<QueensProblemSolution> solutions;


    public QueensProblemSolver(int boardDimension) {
        queens = new int[boardDimension];
        for (int i = 0; i < queens.length; i++) {
            queens[i] = i;
        }

        solutions = new ArrayList<>();
    }

    /**
     * Finds all solutions for Queens Problem and returns it in form
     * of List of arrays of queens positions.
     * @return
     */
    public List<QueensProblemSolution> findAllSolutions() {
        permutation(queens.length, queens);
        return solutions;
    }

    /**
     * Method that recursively rearranges numbers. If current
     * arrangement is solution for queens problem then it
     * adds it to the list of solutions.
     *
     * @param n     Number of elements to rearrange
     * @param arr   Array of elements to be rearranged
     */
    private void permutation(int n, int[] arr) {
        if (n == 1) {
            if (isArrangementCompleted())
                solutions.add(new QueensProblemSolution(arr));

            return;
        }

        for (int i = 0; i < n - 1; i++) {
            permutation(n - 1, arr);
            if (n % 2 == 0)
                swap(arr, i, n - 1);
            else
                swap(arr, 0, n - 1);
        }
        permutation(n - 1, arr);
    }

    /**
     * Swaps to elements in array in given positions.
     *
     * @param arr   Array containing numbers
     * @param i     Position of first element
     * @param j     Position of second element
     */
    private void swap(int[] arr, int i, int j) {
        int value = arr[i];
        arr[i] = arr[j];
        arr[j] = value;
    }

    /**
     * @return  Determines whether arrangement of queens is completed
     */
    private boolean isArrangementCompleted() {
        for (int i = 0; i < queens.length; i++) {
            if (!isPositionSecure(queens[i], i))
                return false;
        }

        return true;
    }

    /**
     * Determines whether given position on chessboard is secure from attacks
     * of other queens.
     *
     * @param x     Column of chessboard
     * @param y     Row on chessboard
     * @return      Is given position on chessboard free
     */
    private boolean isPositionSecure(int x, int y) {
        for (int i = 0; i < y; i++) {
            if (queens[i] == x || (queens[i] != 0 && (x + y == i + queens[i] || Math.abs(x - queens[i]) == Math.abs(y - i)))) {
                return false;
            }
        }

        for (int i = y + 1; i < queens.length; i++) {
            if (queens[i] == x || (queens[i] != 0 && (x + y == i + queens[i] || Math.abs(x - queens[i]) == Math.abs(y - i)))) {
                return false;
            }
        }

        return true;
    }
}
