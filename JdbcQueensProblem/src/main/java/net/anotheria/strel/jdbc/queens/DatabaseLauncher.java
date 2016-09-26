package net.anotheria.strel.jdbc.queens;


import net.anotheria.strel.basic.queenproblem.QueensProblemSolution;
import net.anotheria.strel.basic.queenproblem.QueensProblemSolver;
import net.anotheria.strel.jdbc.DatabaseConfig;

import java.util.List;

/**
 * Saves eight Queens problem for chessboard with dimensions (8x8)
 * to the PostgreSQL database with given name under given user login and password.
 *
 * Arguments for program should be specified in given format
 *      java DatabaseLauncher <dbName> <userName> <password>
 *
 * @author Strel97
 */
public class DatabaseLauncher {
    public static void main(String[] args) {
        if (args.length < 3)
            return;

        QueensDatabaseManager dbManager = new QueensDatabaseManager();
        QueensProblemSolver solver = new QueensProblemSolver(8);

        // dbManager.connect(DatabaseConfig.getInstance());
        dbManager.connect(args[0], args[1], args[2]);
        dbManager.saveSolutions("EightQueens", solver.findAllSolutions());

        // Getting saved solutions
        List<QueensProblemSolution> solutions = dbManager.getSolutions("EightQueens");
        for (int i = 0; solutions != null && i < solutions.size(); i++) {
            System.out.println(i + ": " + solutions.get(i));
        }

        dbManager.closeManager();
    }
}
