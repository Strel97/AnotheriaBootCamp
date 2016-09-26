package net.anotheria.strel.basic.queenproblem;

import java.util.List;

/**
 * Used for printing given solutions for the queens problem into standard input / output stream
 * in the form of chessboard coordinates (X is alphabetical character and Y is number of row).
 *
 * @author Strel97
 */
public class QueensProblemPrinter {
    /**
     * Prints all solutions from given set.
     * @param solutions Set of solutions
     */
    public static void printSolutions(List<QueensProblemSolution> solutions) {
        int solNum = 0;
        System.out.println("TOTAL SOLUTIONS: " + solutions.size());
        for (QueensProblemSolution solution : solutions) {
            System.out.printf("Solution #%d: %s\n", ++solNum, solution);
        }
    }
}
