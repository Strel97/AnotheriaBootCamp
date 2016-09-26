package net.anotheria.strel.basic.queenproblem;

import java.util.List;

/**
 * @author Strel97
 */
public class QueensProblemLauncher {
    public static void main(String[] args) {
        QueensProblemSolver queensProblem = new QueensProblemSolver(8);
        List<QueensProblemSolution> solutions = queensProblem.findAllSolutions();

        QueensProblemPrinter.printSolutions(solutions);
    }
}
